package com.ausware.yao.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class TtlQueueConfig {

    //普通交换机名称
    public final static String EXCHANGE_NAME = "X";
    //死信交换机名称
    public final static String DEAD_EXCHANGE_NAME = "Y";
    //普通队列名称
    public final static String QUEUE_NAME_A = "QA";
    public final static String QUEUE_NAME_B = "QB";
    public final static String QUEUE_NAME_C = "QC";
    //死信队列名称
    public final static String DEAD_QUEUE_NAME = "QD";

    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(DEAD_EXCHANGE_NAME);
    }

    //设置过期时间 10s
    @Bean("queueA")
    public Queue queueA(){
        HashMap<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE_NAME);
        //设置死信路由key
        arguments.put("x-dead-letter-routing-key","YD");
        //设置过期时间
        arguments.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_NAME_A).withArguments(arguments).build();
    }

    @Bean("queueB")
    public Queue queueB(){
        HashMap<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE_NAME);
        //设置死信路由key
        arguments.put("x-dead-letter-routing-key","YD");
        //设置过期时间
        arguments.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_NAME_B).withArguments(arguments).build();

    }
    @Bean("queueC")
    public Queue queueC(){
        HashMap<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE_NAME);
        //设置死信路由key
        arguments.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QUEUE_NAME_C).withArguments(arguments).build();

    }

    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_QUEUE_NAME).build();
    }


    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }

}
