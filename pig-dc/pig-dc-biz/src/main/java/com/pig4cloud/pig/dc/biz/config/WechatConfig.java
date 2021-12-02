package com.pig4cloud.pig.dc.biz.config;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * WechatConfig
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/11/21 20:20
 * Copyright :  版权所有
 **/
/*@RefreshScope
@Configuration
@NacosConfigurationProperties(prefix = "wechat", dataId = "pig-dc-biz-dev.yml",ignoreUnknownFields = false)*/


/*wechat:
		pay:
		v3:
		# App租户租户标识为 mobile
		mobile:
		app-id: wx550971cf65eb2366
		app-secret: acb13b57948fdc938e2fd10cee833689
		app-v3-secret: wN2yX6cY5tH4kJ3wI2tR5oJ8sF1oD4kF
		mch-id: 1225960502
		domain: https://rdch.sfwal.com
		cert-path: apiclient_cert.p12
		# 微信小程序租户标识为 miniapp
		miniapp:
		app-id: wx550971cf65eb2366
		app-secret: acb13b57948fdc938e2fd10cee833689
		app-v3-secret: wN2yX6cY5tH4kJ3wI2tR5oJ8sF1oD4kF
		mch-id: 1225960502
		domain: https://rdch.sfwal.com
		cert-path: apiclient_cert.p12*/
@Data
@RefreshScope
@Component
public class WechatConfig {

	@Value("${wechat.appId}")
	private String appId;
	@Value("${wechat.appSecret}")
	private String appSecret;
	@Value("${wechat.token}")
	private String token;
	@Value("${wechat.aesKey}")
	private String aesKey;

	@Value("${wechat.pay.v3.miniapp.app-v3-secret}")
	private String appSecretV3;
	@Value("${wechat.pay.v3.miniapp.mch-id}")
	private String mchId;
	@Value("${wechat.pay.v3.miniapp.domain}")
	private String domain;
	@Value("${wechat.pay.v3.miniapp.cert-path}")
	private String certPath;

}
