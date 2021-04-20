package com.ausware.yao.service.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

import javax.xml.ws.BindingType;

@RabbitListener(bindings = @QueueBinding(
                value = @Queue(name = "duanxin_topic_queue",durable = "true",autoDelete = "false"),
                exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
                key = "#.duanxin.#")
)
@Service
public class TopicDuanxinConsumer {

    @RabbitHandler
    public void receiveMessage(String message){
        System.out.println("duanxin  direct --- 》 接收到了订单信息，信息是："+message);
    }
}
