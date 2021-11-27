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

package com.pig4cloud.pig.common.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pig4cloud.pig.common.core.config.RtErrorHandler;
import com.pig4cloud.pig.common.security.exception.InvalidException;
import com.pig4cloud.pig.common.security.exception.UnauthorizedException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author lengleng
 * @date 2020-06-23
 */
@Slf4j
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class PigResourceServerAutoConfiguration {

	@Bean("pms")
	public PermissionService permissionService() {
		return new PermissionService();
	}

	@Bean
	public PigAccessDeniedHandler pigAccessDeniedHandler(ObjectMapper objectMapper) {
		return new PigAccessDeniedHandler(objectMapper);
	}

	@Bean
	public PigBearerTokenExtractor pigBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
		return new PigBearerTokenExtractor(urlProperties);
	}

	@Bean
	public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper) {
		return new ResourceAuthExceptionEntryPoint(objectMapper);
	}

	@Bean
	@Primary
	@LoadBalanced
	public RestTemplate lbRestTemplate() {

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setOutputStreaming(false); // 解决401报错时，报java.net.HttpRetryException: cannot retry due to server authentication, in streaming mode

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		////////////////////////////////////
		// ///////////////////////////////////////////////////////

		//RestTemplate restTemplate = new RestTemplate();

		// 传递ACCEPT JSON
		restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
			request.getHeaders().set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			return execution.execute(request, body);
		}));

		// 处理400 异常
		/*restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			@SneakyThrows
			public void handleError(ClientHttpResponse response) {
				if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value()) {
					super.handleError(response);
				}
			}
		});*/

		//跳过401
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			@SneakyThrows
			public void handleError(ClientHttpResponse response) {
				log.info("===========restTemplate.handleError,response.getRawStatusCode():{}",response.getRawStatusCode());

				//处理401的问题
				if (response.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()){
					//super.handleError(response);
					//throw new RuntimeException(String.valueOf(response.getRawStatusCode()));
					throw new UnauthorizedException("无效的token",null);
				}
				else if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value() ){
					super.handleError(response);
				}
			}
		});
		//restTemplate.setErrorHandler(new RtErrorHandler());

		return restTemplate;
	}

}
