package com.ausware.yao.consumer;

import com.ausware.yao.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    public void receive(Message message){
        String msg = new String(message.getBody());
        log.info("接收到的队列confirm.queue 消息：{}"+msg);
    }
}
