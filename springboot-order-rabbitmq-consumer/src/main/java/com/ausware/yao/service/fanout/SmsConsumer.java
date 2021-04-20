package com.ausware.yao.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "sms.fanout.queue")
@Service
public class SmsConsumer {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("sms  fanout --- 》 接收到了订单信息，信息是："+message);
    }
}
