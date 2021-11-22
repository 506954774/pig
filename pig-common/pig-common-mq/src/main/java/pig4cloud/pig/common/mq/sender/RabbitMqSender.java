package pig4cloud.pig.common.mq.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author LeiChen
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2019/4/3016:30
 */
@Component
@Slf4j
public class RabbitMqSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送mq消息
     *
     * @param queue
     * @param msg
     */
    public void send(String queue, String msg) {
        amqpTemplate.convertAndSend(queue, msg);
    }

    /**
     * 发送mq消息
     *
     * @param exchange
     * @param bindingKey
     * @param msg
     */
    public void send(String exchange, String bindingKey, String msg) {
        amqpTemplate.convertAndSend(exchange, bindingKey, msg);
    }

    /**
     * 发送延迟消息
     *
     * @param exchange 交换机
     * @param bindingKey 队列绑定的路由key
     * @param content 发送的消息内容
     * @param delayTime 延迟时间（单位：毫秒）
     *
     * @author feitao <yyimba@qq.com> 2019-8-15 11:05:55
     */
    public void sendDelayMessage(String exchange, String bindingKey, String content, Long delayTime) {
        log.info("===============延时队列生产消息====================");
        log.info("发送时间:{}, 发送内容:{}", LocalDateTime.now(), content);
        amqpTemplate.convertAndSend(
                exchange,
                bindingKey,
                content,
                message -> {
                    message.getMessageProperties().setHeader("x-delay", delayTime);
                    return message;
                }
        );
        log.info("{}ms后执行", delayTime);
    }


    public void sendDelayMessage(String exchange, String bindingKey, String content, Long delayTime,Long startTime) {
        log.info("===============延时队列生产消息====================");
        log.info("发送时间:{}, 发送内容:{}", LocalDateTime.now(), content);
        amqpTemplate.convertAndSend(
                exchange,
                bindingKey,
                content,
                message -> {
                    message.getMessageProperties().setHeader("x-delay", startTime);
                    return message;
                }
        );
        log.info("{}ms后执行", startTime);
    }
}