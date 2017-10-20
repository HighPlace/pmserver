package com.highplace.biz.pm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQReceiver {

    public static final Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues="batchImportQueue")    //监听器监听指定的Queue
    public void process(String str) {
        logger.debug("Receive MQ message:"+str);
    }
}
