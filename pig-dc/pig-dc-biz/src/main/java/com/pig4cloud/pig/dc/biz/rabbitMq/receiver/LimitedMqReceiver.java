package com.pig4cloud.pig.dc.biz.rabbitMq.receiver;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.dc.api.entity.OscOrder;
import com.pig4cloud.pig.dc.biz.enums.OrderStatusEnum;
import com.pig4cloud.pig.dc.biz.mapper.OscOrderMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pig4cloud.pig.common.mq.constants.RabbitMqConstants;

import java.io.IOException;

@Component
@RabbitListener(queues = RabbitMqConstants.QUEUE_LIMITED)
public class LimitedMqReceiver {

    private Logger log = LoggerFactory.getLogger(LimitedMqReceiver.class.getName());


    @Autowired
	private OscOrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    @RabbitHandler
    public void receiver(String param, Message message, Channel channel) throws IOException {
		log.info("====LimitedMqReceiver 消费消息===={}",param);

    	//设置每次处理4个
		channel.basicQos(0, 4, false);

		log.info("============================================LimitedMqReceiver模拟耗时操作" );
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {

		}

		//应答
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);


	}

}
