package com.pig4cloud.pig.dc.biz.service.impl;

import com.pig4cloud.pig.dc.api.entity.WechatAccessTokenResponse;
import com.pig4cloud.pig.dc.biz.config.WechatConfig;
import com.pig4cloud.pig.dc.biz.mapper.OscCollegeMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscMajorMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscUserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *     微信token
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebcastRoomTokenServiceImpl {

	private final OscUserInfoMapper oscUserInfoMapper;
	private final OscMajorMapper oscMajorMapper;
	private final OscCollegeMapper oscCollegeMapper;

	private final WechatConfig wechatConfig;


	@Cacheable(cacheNames = {"queryWecahtAccessToken"})
	public String queryWecahtAccessToken(){

		// 请求地址
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=$1&secret=$2";
		url=url.replace("$1",wechatConfig.getAppId());
		url=url.replace("$2",wechatConfig.getAppSecret());
		log.info("queryWecahtAccessToken,url:{}",url);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

		RestTemplate restTemplate =new RestTemplate();
		ResponseEntity<WechatAccessTokenResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, WechatAccessTokenResponse.class);
		log.info("queryWecahtAccessToken接口返回值,{}",responseEntity);

		if(responseEntity!=null){
			return responseEntity.getBody().getAccess_token();
		}
		return null;

	}





}
