package com.ausware.yao.service.MessageAck;

import com.ausware.yao.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {

    public final static String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        //队列持久化
        boolean durable = true;
        //开启发布确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        //从控制台中输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            //消息持久化 MessageProperties.PERSISTENT_TEXT_PLAIN
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息："+message);
        }

    }
}
