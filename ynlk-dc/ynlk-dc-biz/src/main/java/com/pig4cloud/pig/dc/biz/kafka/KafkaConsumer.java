package com.pig4cloud.pig.dc.biz.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * KafkaConsumer
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2022/2/24 10:30
 * Copyright :  版权所有
 **/
@Slf4j
@Component
public class KafkaConsumer {

	//kafka的监听器，topic为"zhTest"，消费者组为"zhTestGroup"
	@KafkaListener(topics = "topic1", groupId = "zhTestGroup")
	public void listenZhugeGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
		String value = record.value();
		log.info( "========================================================record.key:{},record.value:{}",record.key(),record.value());
		//手动提交offset
		ack.acknowledge();
	}

    /*//配置多个消费组
    @KafkaListener(topics = "zhTest",groupId = "zhTestGroup2")
    public void listenTulingGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
        ack.acknowledge();
    }*/
}
