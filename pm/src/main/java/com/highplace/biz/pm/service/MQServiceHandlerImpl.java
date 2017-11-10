package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.highplace.biz.pm.service.common.MQService;
import com.highplace.biz.pm.service.common.MQServiceHandler;
import com.highplace.biz.pm.service.common.TaskStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//消息处理接口的业务实现
@Service
public class MQServiceHandlerImpl implements MQServiceHandler {

    public static final Logger logger = LoggerFactory.getLogger(MQServiceHandlerImpl.class);

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ChargeService chargeService;

    @Override
    public void batchImportQueueHandler(String msg) {

        try {
            JSONObject jsonObject = JSON.parseObject(msg);
            String target = jsonObject.getString(MQService.MSG_KEY_TARGET);

            TaskStatusService.TaskTargetEnum taskTargetEnum = TaskStatusService.TaskTargetEnum.valueOf(target);
            switch (taskTargetEnum) {
                case CUSTOMER:
                    customerService.batchImport(jsonObject);
                    break;

                case PROPERTY:
                    propertyService.batchImport(jsonObject);
                    break;

                case EMPLOYEE:
                    employeeService.batchImport(jsonObject);
                    break;

                case CHARGE:
                    chargeService.batchImport(jsonObject);
                    break;

                default:
                    break;
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
            String target = jsonObject.getString(MQService.MSG_KEY_TARGET);

            TaskStatusService.TaskTargetEnum taskTargetEnum = TaskStatusService.TaskTargetEnum.valueOf(target);
            switch (taskTargetEnum) {
                case CUSTOMER:
                    customerService.batchExport(jsonObject);
                    break;

                case PROPERTY:
                    propertyService.batchExport(jsonObject);
                    break;

                case EMPLOYEE:
                    employeeService.batchExport(jsonObject);
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            logger.error("batchExportQueueHandler Process error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void chargeCalculateQueueHandler(String msg) {

        try {
            JSONObject jsonObject = JSON.parseObject(msg);
            chargeService.chargeCalculate(jsonObject);
        } catch (Exception e) {
            logger.error("batchExportQueueHandler Process error:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
