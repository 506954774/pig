/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.gateway.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.exception.ValidateCodeException;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.util.WebUtils;
import com.pig4cloud.pig.gateway.config.GatewayConfigProperties;
import com.pig4cloud.pig.gateway.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * The type Validate code gateway filter.
 *
 * @author lengleng
 * @date 2018 /7/4 验证码处理
 */
@Slf4j
@RequiredArgsConstructor
public class ValidateCodeGatewayFilter extends AbstractGatewayFilterFactory<Object> {

	private final GatewayConfigProperties configProperties;

	private final ObjectMapper objectMapper;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			boolean isAuthToken = CharSequenceUtil.containsAnyIgnoreCase(request.getURI().getPath(),
					SecurityConstants.OAUTH_TOKEN_URL);

			// 不是登录请求，直接向下执行
			if (!isAuthToken) {
				return chain.filter(exchange);
			}

			//使用redis分布式锁,校验请求的频率
			checkLoginRequest(request);


			// 刷新token，直接向下执行
			String grantType = request.getQueryParams().getFirst("grant_type");
			if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
				return chain.filter(exchange);
			}

			boolean isIgnoreClient = configProperties.getIgnoreClients().contains(WebUtils.getClientId(request));
			try {
				// only oauth and the request not in ignore clients need check code.
				if (!isIgnoreClient) {
					checkCode(request);
				}
			}
			catch (Exception e) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

				final String errMsg = e.getMessage();
				return response.writeWith(Mono.create(monoSink -> {
					try {
						byte[] bytes = objectMapper.writeValueAsBytes(R.failed(errMsg));
						DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);

						monoSink.success(dataBuffer);
					}
					catch (JsonProcessingException jsonProcessingException) {
						log.error("对象输出异常", jsonProcessingException);
						monoSink.error(jsonProcessingException);
					}
				}));
			}

			return chain.filter(exchange);
		};
	}

	@SneakyThrows
	private void checkCode(ServerHttpRequest request) {
		String code = request.getQueryParams().getFirst("code");

		if (CharSequenceUtil.isBlank(code)) {
			throw new ValidateCodeException("验证码不能为空");
		}

		String randomStr = request.getQueryParams().getFirst("randomStr");
		if (CharSequenceUtil.isBlank(randomStr)) {
			randomStr = request.getQueryParams().getFirst("mobile");
		}

		String key = CacheConstants.DEFAULT_CODE_KEY + randomStr;
		if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
			throw new ValidateCodeException("验证码不合法");
		}

		Object codeObj = redisTemplate.opsForValue().get(key);

		if (codeObj == null) {
			throw new ValidateCodeException("验证码不合法");
		}

		String saveCode = codeObj.toString();
		if (CharSequenceUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			throw new ValidateCodeException("验证码不合法");
		}

		redisTemplate.delete(key);
	}


	@SneakyThrows
	private void checkLoginRequest (ServerHttpRequest request) {

		//只校验登录接口的调用频率
		if(!SecurityConstants.OAUTH_TOKEN_URL.equals(request.getURI().getPath())){
			return;
		}

		String ip= IpUtil.getIpAddress(request);

		//设置分布式锁,key是锁id,value是当前应用进程标识,lockTime后自动删除.lockTime内能把业务做完,则不会有任何问题
		String redisKey= ip+"_"+request.getURI().getPath();

		log.info("LoginRequestFilter",redisKey);

		boolean notBlocking= redisTemplate.opsForValue().setIfAbsent(redisKey,redisKey,1000, TimeUnit.MILLISECONDS);

		if(!notBlocking){
			throw new RuntimeException("操作过于频繁,请稍后再试");
		}

	}
}
