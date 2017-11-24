package com.highplace.biz.pm.service.util.cloud;

import com.alibaba.fastjson.JSONObject;
import com.highplace.biz.pm.config.AliyunConfig;
import com.highplace.biz.pm.config.QCloudConfig;
import com.highplace.biz.pm.service.common.TaskStatusService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadDownloadTool {

    //上传本地文件到腾讯云,然后删除本地文件
    public static Map<String, Object> uploadToQCloud(QCloudConfig qCloudConfig, String cosFolder, String cosFilePath, String localFilePath) {

        String bucketName = qCloudConfig.getCosBucketName();
        OssHelperInterface ossHelper = new QCloudCosHelper(qCloudConfig.getAppId(), qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());

        //创建cos folder
        ossHelper.createFolder(bucketName, cosFolder);
        //上传文件
        JSONObject jsonUploadResult = ossHelper.uploadFile(bucketName, cosFilePath, localFilePath);
        if (jsonUploadResult.getIntValue("code") == 0) {
            //生成下载导出结果文件的url
            String downloadUrl = ossHelper.getDownLoadUrl(bucketName, cosFilePath, jsonUploadResult.getJSONObject("data").getString("source_url"));
            jsonUploadResult.put(TaskStatusService.TASK_RESULT_FILEURL_KEY, downloadUrl);
        }
        // 关闭释放资源
        ossHelper.releaseCosClient();

        //删除本地文件
        File localFile = new File(localFilePath);
        localFile.delete();
        return UploadDownloadTool.jsonObjectToTaskResultMap(jsonUploadResult);
    }

    //上传本地文件到阿里云,然后删除本地文件
    public static Map<String, Object> uploadToAliyun(AliyunConfig aliyunConfig, String cosFolder, String cosFilePath, String localFilePath) {

        OssHelperInterface ossHelper = new AliyunOssHelper(aliyunConfig.getEndpoint(), aliyunConfig.getAccessKeyId(), aliyunConfig.getAccessKeySecret());
        String bucketName = aliyunConfig.getBucketName();

        //创建cos folder
        ossHelper.createFolder(bucketName, cosFolder);
        //上传文件
        JSONObject jsonUploadResult = ossHelper.uploadFile(bucketName, cosFilePath, localFilePath);
        if (jsonUploadResult.getIntValue("code") == 0) {
            //生成下载导出结果文件的url
            String downloadUrl = ossHelper.getDownLoadUrl(bucketName, cosFilePath, null);
            jsonUploadResult.put(TaskStatusService.TASK_RESULT_FILEURL_KEY, downloadUrl);
        }
        // 关闭释放资源
        ossHelper.releaseCosClient();

        //删除本地文件
        File localFile = new File(localFilePath);
        localFile.delete();
        return UploadDownloadTool.jsonObjectToTaskResultMap(jsonUploadResult);
    }

    //从腾讯云下载文件到本地,然后删除云端文件
    public static boolean downloadFromQCloud(QCloudConfig qCloudConfig, String cosFilePath, String localFilePath) {

        boolean result;
        OssHelperInterface ossHelper = new QCloudCosHelper(qCloudConfig.getAppId(), qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());
        String bucketName = qCloudConfig.getCosBucketName();

        //下载文件到本地
        JSONObject jsonGetFileResult = ossHelper.getFile(bucketName, cosFilePath, localFilePath);
        int code = jsonGetFileResult.getIntValue("code");
        if (code != 0) {
            result = false;
        } else {
            result = true;
            //删除远程的文件
            ossHelper.deleteFile(bucketName, cosFilePath);
        }
        // 关闭释放资源
        ossHelper.releaseCosClient();
        return result;
    }

    //从阿里云下载文件到本地,然后删除云端文件
    public static boolean downloadFromAliyun(AliyunConfig aliyunConfig, String cosFilePath, String localFilePath) {

        boolean result;
        OssHelperInterface ossHelper = new AliyunOssHelper(aliyunConfig.getEndpoint(), aliyunConfig.getAccessKeyId(), aliyunConfig.getAccessKeySecret());
        String bucketName = aliyunConfig.getBucketName();

        //下载文件到本地
        JSONObject jsonGetFileResult = ossHelper.getFile(bucketName, cosFilePath, localFilePath);
        int code = jsonGetFileResult.getIntValue("code");
        if (code != 0) {
            result = false;
        } else {
            result = true;
            //删除远程的文件
            ossHelper.deleteFile(bucketName, cosFilePath);
        }
        // 关闭释放资源
        ossHelper.releaseCosClient();
        return result;
    }


    //将JSONObject转换为任务结果需要的Map结构
    private static Map<String, Object> jsonObjectToTaskResultMap(JSONObject jsonResult) {

        //处理结果Map
        Map<String, Object> result = new HashMap<>();
        int code = jsonResult.getIntValue("code");
        if (code != 0) {
            String errMsg = jsonResult.getString("message");
            String resultMsg = "上传文件失败(qcloud:" + code + "," + errMsg + ")";

            //处理结果为失败
            result.put(TaskStatusService.TASK_RESULT_CODE_KEY, 20000);
            result.put(TaskStatusService.TASK_RESULT_MESSAGE_KEY, resultMsg);
        } else {
            //处理结果为成功
            result.put(TaskStatusService.TASK_RESULT_CODE_KEY, 0);
            result.put(TaskStatusService.TASK_RESULT_MESSAGE_KEY, "SUCCESS");
            result.put(TaskStatusService.TASK_RESULT_FILEURL_KEY, jsonResult.getString(TaskStatusService.TASK_RESULT_FILEURL_KEY));
        }
        return result;
    }
}
