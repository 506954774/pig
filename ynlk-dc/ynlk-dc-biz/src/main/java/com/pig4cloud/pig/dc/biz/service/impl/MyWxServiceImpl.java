package com.pig4cloud.pig.dc.biz.service.impl;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import com.pig4cloud.pig.dc.biz.config.WechatConfig;
import com.pig4cloud.pig.dc.biz.config.WxMaInRedisConfigStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

/**
 * MyWxServiceImpl
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/11/21 18:00
 * Copyright :  版权所有
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MyWxServiceImpl extends WxMaServiceImpl  {

	private final RedisTemplate redisTemplate;

	private  final  WechatConfig wechatConfig;

	@PostConstruct
	public void initServices() {
		WxMaInRedisConfigStorage config = new WxMaInRedisConfigStorage(redisTemplate);
		config.setAppid(wechatConfig.getAppId());
		config.setSecret(wechatConfig.getAppSecret());
		config.setToken(wechatConfig.getToken());
		config.setAesKey(wechatConfig.getAesKey());
		log.info("初始化微信配置,{}",wechatConfig);
		this.setWxMaConfig(config);
	}


}
