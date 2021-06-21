package com.ausware.yao.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DelayQueueConfig {

    //交换机
    public final static String DELAY_QUEUE_NAME = "delayed.queue";
    //队列
    public final static String DELAY_EXCHANGE_NAME = "delayed.exchange";

    //路由key
    public final static String DELAY_ROUTING_KEY = "delayed.routingkey";

    //声明队列
    @Bean
    public Queue queue(){
        return QueueBuilder.durable(DELAY_QUEUE_NAME).build();
    }

    //声明交换机 与基于插件
    @Bean()
    public CustomExchange delayExchange(){
        HashMap<String, Object> arguments = new HashMap<>();
        //延迟类型
        arguments.put("x-delayed-type","direct");
        /**
         * 参数一：交换机名称
         * 参数二：交换机类型
         * 参数三：是否需要持久话
         * 参数四：是否自动删除
         * 参数五：其他参数
         */
        return new CustomExchange(DELAY_EXCHANGE_NAME,"x-delayed-message",true,false,arguments);
    }

    //绑定关系
    @Bean
    public Binding binding(@Qualifier("queue") Queue queue,
                           @Qualifier("delayExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_ROUTING_KEY).noargs();
    }
}
