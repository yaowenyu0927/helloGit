package com.ausware.yao.service.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "duanxin.direct.queue")
@Service
public class DirectDuanxinConsumer {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("duanxin  direct --- 》 接收到了订单信息，信息是："+message);
    }
}
