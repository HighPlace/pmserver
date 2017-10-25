package com.highplace.biz.pm.service.common;

public interface MQServiceHandler {

    void batchImportQueueHandler(String msg);
    void batchExportQueueHandler(String msg);
}
