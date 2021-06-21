package com.ausware.yao.service.dead;

import com.ausware.yao.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Producer {
    //交换机名称
    public final static String EXCHANGE_NAME = "normal_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMQUtils.getChannel();
        //声明交换机，消费者方已经声明
        //死信消息 设置TTL过期时间 10s
        AMQP.BasicProperties properties =
                new AMQP.BasicProperties()
                        .builder().expiration("10000").build();


        for (int i = 1; i < 11; i++) {
            String message  = "info"+i;
            channel.basicPublish(EXCHANGE_NAME,"zhangsan",properties,message.getBytes(StandardCharsets.UTF_8));
        }


    }
}
