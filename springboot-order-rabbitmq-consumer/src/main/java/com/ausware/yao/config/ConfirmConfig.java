package com.ausware.yao.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {

    public final static String  CONFIRM_EXCHANGE = "confirm.exchange";

    public final static String  CONFIRM_QUEUE = "confirm.queue";

    public final static String  CONFIRM_ROUTING_KEY = "key1";

    @Bean
    public Queue queue(){
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }
    @Bean("confirmExchange")
    public DirectExchange directExchange(){
        return new DirectExchange(CONFIRM_EXCHANGE);
    }

    @Bean
    public Binding binding(@Qualifier("queue") Queue queue,
                           @Qualifier("confirmExchange")Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY).noargs();
    }


}
