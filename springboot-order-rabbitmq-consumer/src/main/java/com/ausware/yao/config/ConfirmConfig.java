package com.ausware.yao.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {

    //普通交换机
    public final static String  CONFIRM_EXCHANGE = "confirm.exchange";
    //备份交换机
    public final static String BACKUP_CONFIRM_EXCHANGE ="backup.exchange";
    //普通队列
    public final static String  CONFIRM_QUEUE = "confirm.queue";
    //备份队列
    public final static String BACKUP_CONFIRM_QUEUE ="backup.queue";
    //报警队列
    public static final String WARNING_CONFIRM_QUEUE ="warning.queue";

    public final static String  CONFIRM_ROUTING_KEY = "key1";

    @Bean
    public Queue queue(){
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean
    public Queue backupQueue(){
        return QueueBuilder.durable(BACKUP_CONFIRM_QUEUE).build();
    }

    @Bean
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_CONFIRM_QUEUE).build();
    }


    @Bean("confirmExchange")
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).durable(true)
                .alternate(BACKUP_CONFIRM_EXCHANGE)  //转发到备份交换机
                .build();
    }

    @Bean("backupExchange")
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(BACKUP_CONFIRM_EXCHANGE);
    }

    @Bean
    public Binding binding(@Qualifier("queue") Queue queue,
                           @Qualifier("confirmExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY).noargs();
    }


    @Bean
    public Binding backupExchangeBindingBackupQueue(@Qualifier("backupQueue") Queue backupQueue,
                           @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    public Binding backupExchangeBindingWarningQueue(@Qualifier("warningQueue") Queue warningQueue,
                                                    @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }

}
