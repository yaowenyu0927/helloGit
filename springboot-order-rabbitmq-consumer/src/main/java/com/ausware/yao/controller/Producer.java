package com.ausware.yao.controller;

import com.ausware.yao.config.ConfirmConfig;
import com.ausware.yao.config.DelayQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/confirm")
public class Producer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发布确认， 发送消息
    @GetMapping("/sendConfirmMsg/{message}")
    public void sendMessage(@PathVariable("message") String message){
        CorrelationData correlationData = new CorrelationData("1");
        log.info("当前时间：{}，发送一条消息",new Date().toString(),message);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,ConfirmConfig.CONFIRM_ROUTING_KEY,"消息来自ttl为10s的队列："+message,correlationData);
    }
}
