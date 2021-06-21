package com.ausware.yao.service.dead;

import com.ausware.yao.utils.RabbitMQUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerB {


    //死信队列名称
    public final static String DEAD_QUEUE_NAME = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        System.out.println("消费者B 等待接收消息......");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("消费者B 接收的消息"+new String(message.getBody(),"UTF-8"));
        };

        channel.basicConsume(DEAD_QUEUE_NAME,true,deliverCallback,consumerTag->{});
    }
}
