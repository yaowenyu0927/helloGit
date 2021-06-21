package com.ausware.yao.service.dead;

import com.ausware.yao.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class ConsumerA {

    //普通交换机名称
    public final static String EXCHANGE_NAME = "normal_exchange";
    //死信交换机名称
    public final static String DEAD_EXCHANGE_NAME = "dead_exchange";
    //普通队列名称
    public final static String QUEUE_NAME = "normal_queue";
    //死信队列名称
    public final static String DEAD_QUEUE_NAME = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMQUtils.getChannel();

        //声明队列与交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        HashMap<String, Object> arguments = new HashMap<>();
        //设置过期时间 10s   一般在生产者方设置过期时间
        //arguments.put("x-message-ttl",10000);
        //正常队列设置过期之后的死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE_NAME);
        //设置死信路由key
        arguments.put("x-dead-letter-routing-key","lisi");
        //设置正常队列的最大容量
//        arguments.put("x-max-length",6);
        channel.queueDeclare(QUEUE_NAME,false,false,false,arguments);

        channel.queueDeclare(DEAD_QUEUE_NAME,false,false,false,null);

        //绑定关系
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"zhangsan");
        channel.queueBind(DEAD_QUEUE_NAME,DEAD_EXCHANGE_NAME,"lisi");
        System.out.println("消费者A 等待接收消息......");

        DeliverCallback deliverCallback = (consumerTag,message)->{
            String msg = new String(message.getBody(), "UTF-8");
            if (msg.equals("info5")){
                System.out.println("消费者A 接收的消息"+msg+"此消息是被拒绝的....");
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("消费者A 接收的消息"+msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
        };

        //开启手动应答
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,consumerTag->{});
    }
}
