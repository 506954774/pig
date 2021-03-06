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
import org.apache.http.util.TextUtils;
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
 * @date 2018 /7/4 ???????????????
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

			// ???????????????????????????????????????
			if (!isAuthToken) {
				return chain.filter(exchange);
			}

			//??????redis????????????,?????????????????????
			checkLoginRequest(request);


			// ??????token?????????????????????
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
						log.error("??????????????????", jsonProcessingException);
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
			throw new ValidateCodeException("?????????????????????");
		}

		String randomStr = request.getQueryParams().getFirst("randomStr");
		if (CharSequenceUtil.isBlank(randomStr)) {
			randomStr = request.getQueryParams().getFirst("mobile");
		}

		String key = CacheConstants.DEFAULT_CODE_KEY + randomStr;
		if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
			throw new ValidateCodeException("??????????????????");
		}

		Object codeObj = redisTemplate.opsForValue().get(key);

		if (codeObj == null) {
			throw new ValidateCodeException("??????????????????");
		}

		String saveCode = codeObj.toString();
		if (CharSequenceUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			throw new ValidateCodeException("??????????????????");
		}

		redisTemplate.delete(key);
	}


	@SneakyThrows
	private void checkLoginRequest (ServerHttpRequest request) {

		//????????????????????????????????????
		if(!SecurityConstants.OAUTH_TOKEN_URL.equals(request.getURI().getPath())){
			return;
		}

		String ip= IpUtil.getIpAddress(request);
		if(TextUtils.isEmpty(ip)){
			return;
		}

		//??????????????????,key??????id,value???????????????????????????,lockTime???????????????.lockTime?????????????????????,????????????????????????
		String redisKey= ip+"_"+request.getURI().getPath();

		log.info("LoginRequestFilter",redisKey);

		boolean notBlocking= redisTemplate.opsForValue().setIfAbsent(redisKey,redisKey,1000, TimeUnit.MILLISECONDS);

		if(!notBlocking){
			throw new RuntimeException("??????????????????,???????????????");
		}

	}
}
