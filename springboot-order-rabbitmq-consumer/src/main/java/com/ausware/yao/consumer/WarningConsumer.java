package com.ausware.yao.consumer;

import com.ausware.yao.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.WARNING_CONFIRM_QUEUE)
    public void receiveMessage(Message message){
        String msg = new String(message.getBody());
        log.info("报警发现不可路由消息：{}"+msg);
    }
}
