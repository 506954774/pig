package com.pig4cloud.pig.dc.biz.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * TestTemplateConfig
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/11/26 22:04
 * Copyright :  版权所有
 **/
@Configuration
public class RestTemplateConfig {


	@Bean("restTemplat")
	//@Primary
	@LoadBalanced
	public RestTemplate restTemplate() {

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setOutputStreaming(false); // 解决401报错时，报java.net.HttpRetryException: cannot retry due to server authentication, in streaming mode
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.setErrorHandler(new RtErrorHandler());

		return restTemplate;
	}
}
