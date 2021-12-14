package com.pig4cloud.pig.dc.biz.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shiyuecamus
 * @version 1.0
 * @date 2021/6/25 12:23
 */
@Slf4j
@Configuration
@EnableCaching //启用缓存
public class RedisConfig {

	/**
	 * 自定义缓存管理器
	 */
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

		Set cacheNames = new HashSet<>();
		cacheNames.add("roster");
		//cacheNames.add("distributor");

		ConcurrentHashMap configMap = new ConcurrentHashMap<>();
		//有效期6分钟自定义缓存时间
		//configMap.put("roster", config.entryTtl(Duration.ofMinutes(1L)));
		//configMap.put("roster", config.entryTtl(Duration.ofDays(1L)));
		//出勤率总数
		//configMap.put("attendanceCurrentlyByTotal", config.entryTtl(Duration.ofMinutes(30L)));
		//出勤率首页
		//configMap.put("attendanceCurrentlyByTotalHead", config.entryTtl(Duration.ofMinutes(30L)));
		//出勤率按org类别
		//configMap.put("attendanceCurrentlyByOrg", config.entryTtl(Duration.ofMinutes(30L)));
		//出勤天数统计
		//configMap.put("attendanceDays", config.entryTtl(Duration.ofMinutes(30L)));
		//出勤记录统计

		//汇率缓存,1个小时的时效
		configMap.put("queryExchangeRate", config.entryTtl(Duration.ofHours(1L)));

		//微信开放平台,accessToken的缓存,1个小时的时效
		configMap.put("queryWecahtAccessToken", config.entryTtl(Duration.ofHours(1L)));

		//永久 key1 的有效期是永久的
		//configMap.put("distributor", config);
		//需要先初始化缓存名称，再初始化其它的配置。
		RedisCacheManager cacheManager = RedisCacheManager.builder(factory).initialCacheNames(cacheNames).withInitialCacheConfigurations(configMap).build();
		return cacheManager;
	}
}
