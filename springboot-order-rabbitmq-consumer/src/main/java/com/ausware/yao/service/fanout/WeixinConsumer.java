package com.ausware.yao.service.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "weixin.fanout.queue")
@Service
public class WeixinConsumer {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("weixin  fanout --- 》 接收到了订单信息，信息是："+message);
    }
}
