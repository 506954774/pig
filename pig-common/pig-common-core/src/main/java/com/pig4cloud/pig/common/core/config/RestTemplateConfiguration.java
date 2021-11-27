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

package com.pig4cloud.pig.common.core.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author lengleng
 * @date 2019/2/1 RestTemplate
 */
@Configuration(proxyBeanMethods = false)
public class RestTemplateConfiguration {

	/*@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}*/

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setOutputStreaming(false); // 解决401报错时，报java.net.HttpRetryException: cannot retry due to server authentication, in streaming mode
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.setErrorHandler(new RtErrorHandler());

		return restTemplate;
	}
}
