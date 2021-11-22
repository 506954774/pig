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

}
