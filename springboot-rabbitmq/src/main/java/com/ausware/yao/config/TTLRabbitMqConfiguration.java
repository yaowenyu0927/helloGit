package com.ausware.yao.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.NumberFormat;

import java.util.HashMap;

@Configuration
public class TTLRabbitMqConfiguration {

    @Bean
    public DirectExchange ttlDirectExchange(){
        return new DirectExchange("ttl_direct_exchange");
    }


    //队列过期时间
    @Bean
    public Queue ttlQueue(){
        //设置过期时间ttl
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-message-ttl",10000);    //这里一定是int类型
        args.put("x-dead-letter-exchange","dead_direct_exchange");
        args.put("x-dead-letter-routing-key","dead");   //fanout不需要配置
        return new Queue("ttl.direct.queue",true,false,false,args);
    }

    //队列过期时间
    @Bean
    public Queue ttlMessageQueue(){
        return new Queue("ttl.message.queue",true,false,false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with("ttl");
    }

    @Bean
    public Binding ttlMagBinding(){
        return BindingBuilder.bind(ttlMessageQueue()).to(ttlDirectExchange()).with("ttlmessage");
    }
}
