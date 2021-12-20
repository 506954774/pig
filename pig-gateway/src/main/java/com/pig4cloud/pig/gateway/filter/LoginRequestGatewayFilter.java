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
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.gateway.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * The type Validate code gateway filter.
 *
 * @author lengleng
 * @date 2018 /7/4 登录请求的过滤器
 */
@Slf4j
@RequiredArgsConstructor
public class LoginRequestGatewayFilter extends AbstractGatewayFilterFactory<Object> {


	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public GatewayFilter apply(Object config) {

		log.info("LoginRequestFilter","============apply");

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

			return chain.filter(exchange);
		};
	}

	@SneakyThrows
	private void checkLoginRequest (ServerHttpRequest request) {

		HttpServletRequest httpServletRequest =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		String ip= IpUtil.getIpAddr(httpServletRequest);


		//设置分布式锁,key是锁id,value是当前应用进程标识,lockTime后自动删除.lockTime内能把业务做完,则不会有任何问题
		String redisKey= ip+"_"+request.getURI().getPath();

		log.info("LoginRequestFilter",redisKey);

		boolean notBlocking= redisTemplate.opsForValue().setIfAbsent(redisKey,redisKey,1000, TimeUnit.MILLISECONDS);

		if(!notBlocking){
			throw new RuntimeException("登录频率过高,限流处理");
		}

	}



}
