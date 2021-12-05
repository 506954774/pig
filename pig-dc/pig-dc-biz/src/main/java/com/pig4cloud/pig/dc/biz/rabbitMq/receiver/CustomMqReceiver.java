package com.pig4cloud.pig.dc.biz.rabbitMq.receiver;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.dc.api.entity.OscOrder;
import com.pig4cloud.pig.dc.biz.enums.OrderStatusEnum;
import com.pig4cloud.pig.dc.biz.mapper.OscOrderMapper;
import com.rabbitmq.client.Channel;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pig4cloud.pig.common.mq.constants.RabbitMqConstants;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;

@Component
@RabbitListener(queues = RabbitMqConstants.QUEUE_TEST)
public class CustomMqReceiver {

    private Logger log = LoggerFactory.getLogger(CustomMqReceiver.class.getName());


    @Autowired
	private OscOrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    @RabbitHandler
    public void receiver(String param, Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.info("====消费消息===={}",param);

		MessageType messageType= JSON.parseObject(param,MessageType.class);

		if(messageType!=null){
			switch (messageType.getType()){
				case MessageType.TYPE_OREDER_CANCEL:
					//查出订单id,如果是未支付,则设置为已取消
					OscOrder oscOrder = orderMapper.selectById(messageType.getId());
					if(oscOrder!=null&&oscOrder.getOrderStatus().equals(OrderStatusEnum.PREPAY.getTypeCode().intValue())){
						oscOrder.setOrderStatus(OrderStatusEnum.CANCEL.getTypeCode().intValue());
						orderMapper.updateById(oscOrder);
					}
					break;
			}
		}
    }

}
