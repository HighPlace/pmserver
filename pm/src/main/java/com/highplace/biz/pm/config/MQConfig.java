package com.highplace.biz.pm.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    //用于批量导入档案的队列
    public static String BATCH_IMPORT_QUEUE_NAME = "batchImportQueue";

    @Bean
    public Queue batchImportQueue() {
        return new Queue(BATCH_IMPORT_QUEUE_NAME);
    }
}
