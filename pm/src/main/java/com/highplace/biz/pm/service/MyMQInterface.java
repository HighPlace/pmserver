package com.highplace.biz.pm.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@EnableBinding(MyMQInterface.class)
public interface MyMQInterface{
    @Input("PROPERTY_BATCH_IMPORT")
    SubscribableChannel inputChannel();
    @Output("PROPERTY_BATCH_IMPORT")
    MessageChannel outputChanel();
}
