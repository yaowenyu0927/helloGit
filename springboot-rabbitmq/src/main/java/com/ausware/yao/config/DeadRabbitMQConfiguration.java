package com.ausware.yao.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


@Configuration
public class DeadRabbitMQConfiguration {

    //1.声明交换机
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("dead_direct_exchange", true, false);
    }
    //2.声明队列

    @Bean
    public Queue deadQueue() {
        return new Queue("dead.direct.queue", true);
    }


    @Bean
    public Binding deadBanding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead");
    }

}
