package com.pig4cloud.pig.dc.biz.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * KafkaProducer
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2022/2/24 10:28
 * Copyright :  版权所有
 **/
@Component
public class KafkaProducer {

	private final static String TOPIC_NAME = "topic1"; //topic的名称

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send() {
		//发送功能就一行代码~
		kafkaTemplate.send(TOPIC_NAME,  "key", "test message send~");
	}

	public void send(String key,String value) {
		//发送功能就一行代码~
		kafkaTemplate.send(TOPIC_NAME,  key, value);
	}
}
