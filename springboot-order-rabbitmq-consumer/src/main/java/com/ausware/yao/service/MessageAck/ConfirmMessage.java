package com.ausware.yao.service.MessageAck;

import com.ausware.yao.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * 发布确认模式
 *  1.单个确认
 *  2.批量确认
 *  3.异步确认
 */
public class ConfirmMessage {

    //批量发送消息的个数
    public final static int COUNT = 1000;
    
    public static void main(String[] args) throws Exception {

        //调用单个确认
        individualConfirmation();   //单个确认耗时时间：645ms
        //调用批量确认
        batchConfirmation();   //批量确认耗时时间：130ms
        //调用异步确认
        asynchronousConfirmation();  //异步确认耗时时间：53ms

    }

    /**
     * 单个确认
     */

    public static void  individualConfirmation() throws IOException, TimeoutException, InterruptedException {

        Channel channel = RabbitMQUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            String message = i + "";
            channel.basicPublish("",queueName,null,message.getBytes(StandardCharsets.UTF_8));
            //单个消息马上立即确认
            boolean b = channel.waitForConfirms();
            if (b){
                System.out.println("消息发送确认完毕.....");
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("单个确认耗时时间："+(end-begin)+"ms");
    }


    /**
     * 批量确认
     */
    public static void batchConfirmation() throws  Exception{

        Channel channel = RabbitMQUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);
        channel.confirmSelect();
        //开始时间
        long begin = System.currentTimeMillis();

        //批量确认消息大小
        int batchSize = 100;
        for (int i = 0; i < COUNT; i++) {
            String message = i + "";
            channel.basicPublish("",queueName,null,message.getBytes(StandardCharsets.UTF_8));

            //判断达到100条间隔时，进行确认
            if (i % batchSize == 0){
                //发布确认
                channel.waitForConfirms();
            }
        }
        //结束时间
        long end = System.currentTimeMillis();

        System.out.println("批量确认耗时时间："+(end-begin)+"ms");

    }


    /**
     * 异步发布
     */

    public static void asynchronousConfirmation() throws Exception{
        Channel channel = RabbitMQUtils.getChannel();
        //队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);
        channel.confirmSelect();

        /**
         * 线程安全有序的哈希表  适用于高并发的情况下
         * 1.轻松将序号与消息进行关联
         * 2.轻松批量删除条目 只要给到序号
         * 3.支持高并发
         */

        ConcurrentSkipListMap<Long,String> outstandingConfirms = new ConcurrentSkipListMap<>();


        //开始时间
        long begin = System.currentTimeMillis();

        //消息确认成功 回调函数
        ConfirmCallback ackCallback = (deliverTag,multiple)->{
            if (multiple){
                //2:删除掉已经确认的消息，剩下的就是未确认的消息
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliverTag);
                confirmed.clear();
            }else {
                outstandingConfirms.remove(deliverTag);
            }
            System.out.println("确认的消息："+deliverTag);
        };
        //消息确认失败 回调函数
        ConfirmCallback nckCallback = (deliverTag,multiple)->{
            String message = outstandingConfirms.get(deliverTag);
            System.out.println("未确认的消息是："+message);

        };
        //准备消息监听器  监听那些消息成功 那些失败  异步
        channel.addConfirmListener(ackCallback,nckCallback);

        for (int i = 0; i < COUNT; i++) {
            String message = i + "";
            channel.basicPublish("",queueName,null,message.getBytes(StandardCharsets.UTF_8));
            //1:此处记录下所有发送的消息
            outstandingConfirms.put(channel.getNextPublishSeqNo(),message);
        }

        //结束时间
        long end = System.currentTimeMillis();

        System.out.println("异步确认耗时时间："+(end-begin)+"ms");
    }



}
