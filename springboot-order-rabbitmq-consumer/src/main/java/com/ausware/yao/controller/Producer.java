package com.ausware.yao.controller;

import com.ausware.yao.config.ConfirmConfig;
import com.ausware.yao.config.DelayQueueConfig;
import com.ausware.yao.config.MyCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/confirm")
public class Producer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MyCallBack myCallBack;


    //发布确认， 发送消息
    @GetMapping("/sendConfirmMsg/{message}")
    public void sendMessage(@PathVariable("message") String message){
        //指定消息的id为 1
        CorrelationData correlationData1 = new CorrelationData("1");
        //指定路由key为 key1
        String routingKey1 = "key1";
        log.info("当前时间：{}，发送一条消息",new Date().toString(),message);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,ConfirmConfig.CONFIRM_ROUTING_KEY,"发布确认消息："+message+routingKey1,correlationData1);
        //指定消息的id为 2
        CorrelationData correlationData2 = new CorrelationData("2");
        //指定路由key为 key2
        String routingKey2 = "key2";
        log.info("当前时间：{}，发送一条消息",new Date().toString(),message);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,ConfirmConfig.CONFIRM_ROUTING_KEY,"发布确认消息："+message+routingKey2,correlationData2);

    }
}
