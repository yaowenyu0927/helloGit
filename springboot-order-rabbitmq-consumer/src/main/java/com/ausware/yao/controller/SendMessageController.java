package com.ausware.yao.controller;

import com.ausware.yao.config.DelayQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //开始发送消息
    @GetMapping("/sendMsg/{message}")
    public void sendMessage(@PathVariable("message") String message){
        log.info("当前时间：{}，发送一条消息给两个TTL队列：{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s的队列："+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s的队列："+message);

    }

    //开始发送消息  优化
    @GetMapping("/sendXPirationMsg/{message}/{ttlTime}")
    public void sendMessage(@PathVariable("message") String message,@PathVariable("ttlTime") String ttlTime){
        log.info("当前时间：{}，发送一条消息时长是：{} 毫秒的TTL信息给队列QC：{}",new Date().toString(),ttlTime,message);
        rabbitTemplate.convertAndSend("X","XC","消息来自ttl为10s的队列："+message,msg -> {
            //设置发送消息的延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }


    //开始发送消息  基于插件的消息及延迟的时间
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendMessage(@PathVariable("message") String message,@PathVariable("delayTime") Integer delayTime){
        log.info("当前时间：{}，发送一条消息时长是：{} 毫秒的信息给延迟队列delayed.queue：{}",new Date().toString(),delayTime,message);
        rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_EXCHANGE_NAME,DelayQueueConfig.DELAY_ROUTING_KEY,"消息来自ttl为10s的队列："+message,msg->{
            //设置发送消息的延迟时长
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }


}
