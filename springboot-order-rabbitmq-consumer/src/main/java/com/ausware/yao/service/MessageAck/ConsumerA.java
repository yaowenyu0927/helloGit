package com.ausware.yao.service.MessageAck;

import com.ausware.yao.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsumerA {
    public final static String QUEUE_NAME = "ack_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        System.out.println("消费者 A 等待接收消息，处理时间短....");
        //采用手动应答机制
        boolean aotoAck = false;
        channel.basicConsume(QUEUE_NAME,aotoAck, new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("接收到的消息："+new String(delivery.getBody()));
                    //手动应答代码
                    /**
                     * 参数一：表示消息的标记 tag
                     * 参数二：是否批量应答
                     */
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
                System.out.println("消息接收失败....");
            }
        });
    }
}
