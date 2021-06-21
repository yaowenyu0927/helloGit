package com.ausware.yao.consumer;

import com.ausware.yao.config.DelayQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DelayedQueueConsumer {

    @RabbitListener(queues = DelayQueueConfig.DELAY_QUEUE_NAME)
    public void receiveDelayedQueue(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到延迟队列的消息：{}",new Date().toString(),msg);
    }
}
