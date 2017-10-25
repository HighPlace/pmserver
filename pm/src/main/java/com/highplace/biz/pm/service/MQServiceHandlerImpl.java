package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.highplace.biz.pm.service.common.MQServiceHandler;
import com.highplace.biz.pm.service.common.TaskStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQServiceHandlerImpl implements MQServiceHandler {

    public static final Logger logger = LoggerFactory.getLogger(MQServiceHandlerImpl.class);

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CustomerService customerService;

    @Override
    public void batchImportQueueHandler(String msg) {

        try {
            JSONObject jsonObject = JSON.parseObject(msg);
            String target = jsonObject.getString("target");

            if (target.equals(TaskStatusService.TASK_TARGET_PROPERTY)) {
                propertyService.batchImport(jsonObject);

            } else if (target.equals(TaskStatusService.TASK_TARGET_CUSTOMER)) {
                customerService.batchImport(jsonObject);
            }

        } catch (Exception e) {
            logger.error("batchImportQueueHandler Process error:" + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void batchExportQueueHandler(String msg) {

        try {
            JSONObject jsonObject = JSON.parseObject(msg);
            String target = jsonObject.getString("target");

            if (target.equals(TaskStatusService.TASK_TARGET_PROPERTY)) {
                propertyService.batchExport(jsonObject);

            } else if (target.equals(TaskStatusService.TASK_TARGET_CUSTOMER)) {
                customerService.batchExport(jsonObject);
            }

        } catch (Exception e) {
            logger.error("batchExportQueueHandler Process error:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
