package com.ausware.yao.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "sms.direct.queue")
@Service
public class DirectSmsConsumer {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("sms  direct --- 》 接收到了订单信息，信息是："+message);
    }
}
