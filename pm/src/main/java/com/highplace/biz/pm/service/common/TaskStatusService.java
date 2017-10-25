package com.highplace.biz.pm.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//异步任务状态服务
@Service
public class TaskStatusService {

    public static final Logger logger = LoggerFactory.getLogger(TaskStatusService.class);

    //任务目标对象
    public static final String TASK_TARGET_PROPERTY = "property";
    public static final String TASK_TARGET_CUSTOMER = "customer";

    //任务结果Map的key
    public static final String TASK_STATUS_KEY = "status";
    public static final String TASK_RESULT_KEY = "result";
    public static final String TASK_RESULT_CODE_KEY = "resultCode"; //0:成功 非0:失败
    public static final String TASK_RESULT_MESSAGE_KEY = "resultMessage"; //错误信息
    public static final String TASK_RESULT_FILEURL_KEY = "fileUrl"; //下载文件url

    @Autowired
    private RedisTemplate redisTemplate;

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

    //任务会以hashmap的方式存到cache中
    //任务结果 redis Key前缀, 格式：TASK_TaskType(string)_target(大写)_productInstId_taskID
    private String generateRedisKey(String target, TaskStatusService.TaskTypeEnum taskType, String productInstId, String taskId) {

        if (target.equals(TASK_TARGET_PROPERTY))
            return "TASK_" + taskType.value() + "_" + TASK_TARGET_PROPERTY.toUpperCase() + "_" + productInstId + "_" + taskId;
        else if (target.equals(TASK_TARGET_CUSTOMER))
            return "TASK_" + taskType.value() + "_" + TASK_TARGET_CUSTOMER.toUpperCase() + "_" + productInstId + "_" + taskId;
        else
            return null;
    }

    //查询task状态
    public Map<Object, Object> getTaskStatus(String target,
                                             TaskStatusService.TaskTypeEnum taskType,
                                             String productInstId,
                                             String taskId) {

        String redisKey = generateRedisKey(target, taskType, productInstId, taskId);
        if (redisKey != null)
            return redisTemplate.opsForHash().entries(redisKey);
        else
            return null;
    }

    //设置task状态
    public void setTaskStatus(String target,
                              TaskStatusService.TaskTypeEnum taskType,
                              String productInstId, String taskId,
                              TaskStatusService.TaskStatusEnum taskStatus,
                              Map<String, Object> result) {

        String redisKey = generateRedisKey(target, taskType, productInstId, taskId);

        Map<String, Object> redisKeyMap = new HashMap<String, Object>();
        redisKeyMap.put(TASK_STATUS_KEY, taskStatus.value());
        redisKeyMap.put(TASK_RESULT_KEY, result);
        redisTemplate.opsForHash().putAll(redisKey, redisKeyMap);
        redisTemplate.expire(redisKey, 24, TimeUnit.HOURS); //24小时有效
    }
}
