package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQReceiver {

    public static final Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private PropertyService propertyService;

    //消息处理异常
    //如果收到消息处理有问题，比如写入数据库失败，请抛出RuntimeException异常，MQ会重试，不过重试几次后会失败，这个要注意
    //应用集群问题
    //如果某个应用起来多个实例，如上面的配置，会导致每条消息每个实例都会收到，如果你不想这么做，请在配置里面加上：
    //spring.cloud.stream.bindings.<channelName>.group=分组名
    //每个应用定义一个唯一的分组名，不好和其他应用重复
    @RabbitListener(queues="batchImportQueue")    //监听器监听指定的Queue
    public void process(String msg) {
        logger.debug("Thread:[" + Thread.currentThread().getName() + "] Receive MQ message:" + msg);
        JSONObject jsonObject = JSON.parseObject(msg);
        if (jsonObject == null) return;

        if(jsonObject.getString("target").equals("property")) {
            propertyService.batchImportHandler(jsonObject);
        }

    }
}

