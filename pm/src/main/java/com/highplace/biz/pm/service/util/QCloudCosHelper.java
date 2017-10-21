package com.highplace.biz.pm.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.meta.InsertOnly;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.GetFileLocalRequest;
import com.qcloud.cos.request.StatFileRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QCloudCosHelper {

    public static final Logger logger = LoggerFactory.getLogger(QCloudCosHelper.class);

    private Credentials credentials; //密钥信息
    private ClientConfig clientConfig; //配置信息
    private COSClient cosClient; //cos客户端

    public QCloudCosHelper(String appId, String secretId, String secretKey) {
        // 初始化秘钥信息
        credentials = new Credentials(Long.parseLong(appId), secretId, secretKey);

        // 初始化客户端配置
        clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
        clientConfig.setRegion("gz");
        // 初始化cosClient
        cosClient = new COSClient(clientConfig, credentials);
    }

    //默认为gz
    public QCloudCosHelper(String appId, String secretId, String secretKey, String region) {
        // 初始化秘钥信息
        credentials = new Credentials(Long.parseLong(appId), secretId, secretKey);

        // 初始化客户端配置
        clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
        clientConfig.setRegion(region);
        // 初始化cosClient
        cosClient = new COSClient(clientConfig, credentials);
    }

    //下载文件到本地
    //返回格式:{"code":0,"message":"SUCCESS"}
    public JSONObject getFile(String bucketName, String cosFilePath, String localFilePath) {

        GetFileLocalRequest getFileLocalRequest = new GetFileLocalRequest(bucketName, cosFilePath, localFilePath);
        getFileLocalRequest.setUseCDN(false);
        getFileLocalRequest.setReferer("*.myweb.cn");
        String getFileResult = cosClient.getFileLocal(getFileLocalRequest);
        logger.info("qcloud getFileResult: " + getFileResult); //{"code":0,"message":"SUCCESS"}
        return JSON.parseObject(getFileResult);
    }

    //删除cos远程文件
    //返回格式:{"code":0,"message":"SUCCESS","request_id":"NTllYWJhOGVfY2NhMzNiMGFfYWViOF9lNTc4YWM="}
    public JSONObject deleteFile(String bucketName, String cosFilePath) {

        DelFileRequest delFileRequest = new DelFileRequest(bucketName, cosFilePath);
        String delFileResult = cosClient.delFile(delFileRequest);
        logger.info("qcloud delFileResult: " + delFileResult);
        return JSON.parseObject(delFileResult);
    }

    //上传文件到cos
    //返回格式:{"code":0,"message":"SUCCESS"}
    public JSONObject uploadFile(String bucketName, String cosFilePath, String localFilePath) {

        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, cosFilePath, localFilePath);
        uploadFileRequest.setEnableShaDigest(false);
        String uploadFileResult = cosClient.uploadFile(uploadFileRequest);

        logger.info("qcloud uploadFileResult: " + uploadFileResult); // {"code":0,"message":"SUCCESS","request_id":"NTllYjcxMDNfMTliMjk0MGFfMWM4ZV9jYzhiZDE=","data":{"access_url":"http://pmugc-1254358905.file.myqcloud.com/property_550E8400-E29B-11D4-A716-446655440000-1508602114567.xls","resource_path":"/1254358905/pmugc/property_550E8400-E29B-11D4-A716-446655440000-1508602114567.xls","source_url":"http://pmugc-1254358905.cosgz.myqcloud.com/property_550E8400-E29B-11D4-A716-446655440000-1508602114567.xls","url":"http://gz.file.myqcloud.com/files/v2/1254358905/pmugc/property_550E8400-E29B-11D4-A716-446655440000-1508602114567.xls","vid":"92bd33f0c1bea6680011912f919613f91508602115"}}
        return JSON.parseObject(uploadFileResult);
    }

    //上传buffer到cos
    public JSONObject uploadBuffer(String bucketName, String cosFilePath, byte[] contentBuffer) {

        UploadFileRequest overWriteFileRequest = new UploadFileRequest(bucketName, cosFilePath, contentBuffer);
        // 如果COS上已有文件, 则进行覆盖(默认不覆盖)
        overWriteFileRequest.setInsertOnly(InsertOnly.OVER_WRITE);
        String overWriteFileRet = cosClient.uploadFile(overWriteFileRequest);

        logger.info("qcloud uploadBufferResult: " + overWriteFileRet); //{"code":0,"message":"SUCCESS"}
        return JSON.parseObject(overWriteFileRet);
    }

    //查看文件属性信息
    public JSONObject statFile(String bucketName, String cosFilePath) {

        StatFileRequest statFileRequest = new StatFileRequest(bucketName, cosFilePath);
        String statFileResult = cosClient.statFile(statFileRequest);

        logger.info("qcloud statFileResult: " + statFileResult); //{"code":0,"message":"SUCCESS","request_id":"NTllYjcyMzRfNjIyNWI2NF80NmRjX2M5NzQ1Zg==","data":{"access_url":"http://pmugc-1254358905.file.myqcloud.com/property_550E8400-E29B-11D4-A716-446655440000-1508602420221.xls","authority":"eInvalid","biz_attr":"","ctime":1508602420,"custom_headers":{},"filelen":5120,"filesize":5120,"forbid":0,"mtime":1508602420,"sha":"1e305bf9511321fc731b8a1275c1237ff96a9903","slicesize":5120,"source_url":"http://pmugc-1254358905.cosgz.myqcloud.com/property_550E8400-E29B-11D4-A716-446655440000-1508602420221.xls"}}
        return JSON.parseObject(statFileResult);
    }


    //释放cos客户端
    public void releaseCosClient() {

        // 关闭释放资源
        cosClient.shutdown();
    }

}
