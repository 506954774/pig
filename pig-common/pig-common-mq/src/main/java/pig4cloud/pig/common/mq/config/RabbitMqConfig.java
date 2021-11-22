package pig4cloud.pig.common.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pig4cloud.pig.common.mq.constants.RabbitMqConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuzhen
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2019/5/1410:48
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 活动状态刷新
     * @return
     */
    @Bean
    public CustomExchange dmpExchange(){
        Map<String, Object> arguments = new HashMap<>(1);
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitMqConstants.EXCHANGE_TEST,"x-delayed-message",true,false,arguments);
    }

    @Bean
    public Queue dmpQueue(){
        return new Queue(RabbitMqConstants.QUEUE_TEST,true,false,false);
    }

    @Bean
    public Binding dmpBind(){
        return BindingBuilder.bind(dmpQueue()).to(dmpExchange()).with(RabbitMqConstants.TOPIC_TEST).noargs();
    }



}
