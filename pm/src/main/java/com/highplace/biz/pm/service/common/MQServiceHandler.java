package com.highplace.biz.pm.service.common;

//具体的消息处理接口
public interface MQServiceHandler {

    void batchImportQueueHandler(String msg);
    void batchExportQueueHandler(String msg);
    void chargeCalculateQueueHandler(String msg);
}
