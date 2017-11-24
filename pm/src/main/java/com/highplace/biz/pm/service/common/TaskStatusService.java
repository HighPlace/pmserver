package com.highplace.biz.pm.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//异步任务状态服务
@Service
public class TaskStatusService {

    public static final Logger logger = LoggerFactory.getLogger(TaskStatusService.class);

    //任务结果Map的key
    public static final String TASK_STATUS_KEY = "status";
    public static final String TASK_RESULT_KEY = "result";
    public static final String TASK_RESULT_CODE_KEY = "resultCode"; //0:成功 非0:失败
    public static final String TASK_RESULT_MESSAGE_KEY = "resultMessage"; //错误信息
    public static final String TASK_RESULT_FILEURL_KEY = "fileUrl"; //下载文件url

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQService mqService;

    //任务会以hashmap的方式存到cache中
    //任务结果 redis Key前缀, 格式：TASK_TaskType(string)_TaskTarget(大写)_productInstId_taskID
    private String generateRedisKey(TaskStatusService.TaskTargetEnum taskTargetEnum,
                                    TaskStatusService.TaskTypeEnum taskType,
                                    String productInstId, String taskId) {
        return "TASK_" + taskType.value() + "_" + taskTargetEnum.value() + "_" + productInstId + "_" + taskId;
    }

    //查询task状态
    public Map<Object, Object> getTaskStatus(TaskStatusService.TaskTargetEnum taskTargetEnum,
                                             TaskStatusService.TaskTypeEnum taskType,
                                             String productInstId,
                                             String taskId) {

        return redisTemplate.opsForHash().entries(generateRedisKey(taskTargetEnum, taskType, productInstId, taskId));
    }

    //设置task状态
    public void setTaskStatus(TaskStatusService.TaskTargetEnum taskTargetEnum,
                              TaskStatusService.TaskTypeEnum taskType,
                              String productInstId, String taskId,
                              TaskStatusService.TaskStatusEnum taskStatus,
                              Map<String, Object> result) {

        String redisKey = generateRedisKey(taskTargetEnum, taskType, productInstId, taskId);

        Map<String, Object> redisKeyMap = new HashMap<String, Object>();
        redisKeyMap.put(TASK_STATUS_KEY, taskStatus.value());
        redisKeyMap.put(TASK_RESULT_KEY, result);
        redisTemplate.opsForHash().putAll(redisKey, redisKeyMap);
        redisTemplate.expire(redisKey, 24, TimeUnit.HOURS); //24小时有效
    }

    //将批量导入请求通过消息队列发出
    public Map<String, Object> sendTaskToMQ(TaskStatusService.TaskTargetEnum taskTargetEnum,
                                            TaskStatusService.TaskTypeEnum taskTypeEnum,
                                            String productInstId,
                                            String fileUrl,
                                            Integer vendor,
                                            Map<String, Object> extraMap) {

        //生成消息和任务ID，使用同一个ID
        String msgAndTaskId = UUID.randomUUID().toString();

        boolean validFlag = false;
        switch (taskTypeEnum) {
            case IMPORT:

                //发送请求到消息队列
                mqService.sendImportMessage(msgAndTaskId,
                        productInstId,
                        taskTargetEnum.value(),
                        fileUrl,
                        vendor,
                        extraMap);
                validFlag = true;
                break;

            case EXPORT:

                //发送请求到消息队列
                mqService.sendExportMessage(msgAndTaskId,
                        productInstId,
                        taskTargetEnum.value(),
                        vendor,
                        extraMap);
                validFlag = true;
                break;

            default:
                break;

        }

        if (validFlag) {
            //设置任务为等待处理中
            setTaskStatus(taskTargetEnum,
                    taskTypeEnum,
                    productInstId,
                    msgAndTaskId,
                    TaskStatusService.TaskStatusEnum.WAIT,
                    null);

            Map<String, Object> result = new HashMap<>();
            result.put("taskId", msgAndTaskId);
            return result;

        } else {
            return null;
        }
    }

    //任务目标 TASK_TARGET_
    public static enum TaskTargetEnum {
        PROPERTY("PROPERTY"), CUSTOMER("CUSTOMER"), EMPLOYEE("EMPLOYEE"), CHARGE("CHARGE"), CHARGE_DETAIL("CHARGE_DETAIL");

        private final String value;

        private TaskTargetEnum(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    //任务类型:目前是IMPORT和EXPORT两种
    public static enum TaskTypeEnum {
        IMPORT("IMPORT"), EXPORT("EXPORT");

        private final String value;

        private TaskTypeEnum(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    //任务状态: -1:等待执行 0:执行中  1:执行结束
    public static enum TaskStatusEnum {
        WAIT(-1), DOING(0), DONE(1);

        private final int value;

        TaskStatusEnum(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }
}
