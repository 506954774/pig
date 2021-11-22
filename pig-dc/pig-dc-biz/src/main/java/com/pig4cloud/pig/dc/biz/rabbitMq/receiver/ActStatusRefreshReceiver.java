package com.pig4cloud.pig.dc.biz.rabbitMq.receiver;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pig4cloud.pig.common.mq.constants.RabbitMqConstants;

import java.io.IOException;

@Component
@RabbitListener(queues = RabbitMqConstants.QUEUE_TEST)
public class ActStatusRefreshReceiver {

    private Logger log = LoggerFactory.getLogger(ActStatusRefreshReceiver.class.getName());



    @Transactional(rollbackFor = Exception.class)
    @RabbitHandler
    public void receiver(String param, Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.info("====消费消息===={}",param);

    }

}
