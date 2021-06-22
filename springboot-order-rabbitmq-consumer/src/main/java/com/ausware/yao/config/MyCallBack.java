package com.ausware.yao.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//回调接口
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        //注入
        rabbitTemplate.setConfirmCallback(this);
        /**
         * true：交换机无法将消息进行路由时，会将该消息返回给生产者
         * false：如果发现消息无法进行路由，则直接丢弃
         */
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(this);
    }
    /**
     * 交换机确认回调方法
     * 1.发消息 交换机接收到了  回调
     *  1.1 correlationData 保存着回调消息的ID及相关信息
     *  1.2 ack 交换机收到消息 true
     *  1.3 cause  null
     * 2.发消息 交换机接收失败  回调
     *  2.1 correlationData 保存着回调消息的ID及相关信息
     *  2.2 ack 交换机收到消息 false
     *  2.3 cause  失败原因
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        String id = correlationData.getId()!=null?correlationData.getId():"";
        if (ack){
            log.info("交换机已经收到了消息 ID ：{}"+id);
        }else {
            log.info("交换机还未收到ID 为：{}的消息，由于原因：{}"+id,cause);
        }
    }

    //只有不可达目的地的时候 才进行回退
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息{}，被交换机{}退回，退回原因：{}，路由key{}"+returned.getMessage(),returned.getExchange(),returned.getReplyText(),returned.getRoutingKey());
    }
}
