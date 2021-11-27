package com.pig4cloud.pig.dc.biz.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * RtErrorHandler
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/11/26 22:07
 * Copyright :  版权所有
 **/
public class RtErrorHandler extends DefaultResponseErrorHandler {


	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {

		return super.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {

		HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
		List<HttpStatus> donotDeal = new ArrayList<>(); // 白名单
		donotDeal.add(HttpStatus.UNAUTHORIZED);

		if (!donotDeal.contains(statusCode)) {
			// 非白名单则处理
			super.handleError(response);
		}
	}

}