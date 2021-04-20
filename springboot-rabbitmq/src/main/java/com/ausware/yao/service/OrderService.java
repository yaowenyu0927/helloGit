package com.ausware.yao.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 模拟用户下单
     *
     * @param usserid
     * @param productid
     * @param num
     */
    public void makeOrder(String usserid, String productid, int num) {
        //1.根据商品id查询库存是否充足
        //2.保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功.....");
        //3.通过MQ完成消息分发
        //参数一：交换机
        //参数二：路由key/queue名称
        //参数三：消息内容
        String exchangeName = "fanout_order_exchange";
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }


    public void makeOrderDirect(String usserid, String productid, int num) {
        //1.根据商品id查询库存是否充足
        //2.保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功.....");
        //3.通过MQ完成消息分发
        //参数一：交换机
        //参数二：路由key/queue名称
        //参数三：消息内容
        String exchangeName = "direct_order_exchange";
        String routingKey = "sms";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }


    public void makeOrderTopic(String usserid, String productid, int num) {
        //1.根据商品id查询库存是否充足
        //2.保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功.....");
        //3.通过MQ完成消息分发
        //参数一：交换机
        //参数二：路由key/queue名称
        //参数三：消息内容
        String exchangeName = "topic_order_exchange";
        String routingKey = "com.duanxin.xxxx";
        /*
        #.duanxin.#
        *.email.#
        com.#
        com.*
         */
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }


    public void makeOrderTTL(String usserid, String productid, int num) {
        //1.根据商品id查询库存是否充足
        //2.保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功.....");
        //3.通过MQ完成消息分发
        //参数一：交换机
        //参数二：路由key/queue名称
        //参数三：消息内容
        String exchangeName = "ttl_direct_exchange";
        String routingKey = "ttl";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId);
    }


    public void makeOrderTTLMessage(String usserid, String productid, int num) {
        //1.根据商品id查询库存是否充足
        //2.保存订单
        String orderId = UUID.randomUUID().toString();
        System.out.println("订单生成成功.....");
        //3.通过MQ完成消息分发
        //参数一：交换机
        //参数二：路由key/queue名称
        //参数三：消息内容
        String exchangeName = "ttl_direct_exchange";
        String routingKey = "ttlmessage";

        //给消息设置过期时间
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {

                //字符串
                message.getMessageProperties().setExpiration("5000");
                //设置编码
                message.getMessageProperties().setContentEncoding("utf-8");
                return message;
            }
        };

        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderId,messagePostProcessor);
    }

}
