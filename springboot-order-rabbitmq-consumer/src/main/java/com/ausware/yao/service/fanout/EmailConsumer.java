package com.ausware.yao.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "email.fanout.queue")
@Service
public class EmailConsumer {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("email  fanout --- 》 接收到了订单信息，信息是："+message);
    }
}
