package com.highplace.biz.pm.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.highplace.biz.pm.config.QCloudConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.GetFileLocalRequest;
import com.qcloud.cos.sign.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class QCloudCosHelper {

    public static final Logger logger = LoggerFactory.getLogger(QCloudCosHelper.class);

    @Autowired
    QCloudConfig qCloudConfig;

    private Credentials credentials; //密钥信息
    private ClientConfig clientConfig; //配置信息
    private COSClient cosClient; //cos客户端

    public QCloudCosHelper() {
        // 初始化秘钥信息
        credentials = new Credentials(Long.parseLong(qCloudConfig.getAppId()), qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());

        // 初始化客户端配置
        clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
        clientConfig.setRegion("gz");
        // 初始化cosClient
        cosClient = new COSClient(clientConfig, credentials);
    }

    //默认为gz
    public QCloudCosHelper(String region) {
        // 初始化秘钥信息
        credentials = new Credentials(Long.parseLong(qCloudConfig.getAppId()), qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());

        // 初始化客户端配置
        clientConfig = new ClientConfig();
        // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
        clientConfig.setRegion(region);
        // 初始化cosClient
        cosClient = new COSClient(clientConfig, credentials);
    }

    //下载文件到本地
    //返回格式:{"code":0,"message":"SUCCESS"}
    public JSONObject getFile(String cosFilePath, String localFilePath) {

        GetFileLocalRequest getFileLocalRequest = new GetFileLocalRequest(qCloudConfig.getCosBucketName(), cosFilePath, localFilePath);
        getFileLocalRequest.setUseCDN(false);
        getFileLocalRequest.setReferer("*.myweb.cn");
        String getFileResult = cosClient.getFileLocal(getFileLocalRequest);
        logger.info("qcloud getFileResult: " + getFileResult); //{"code":0,"message":"SUCCESS"}
        return JSON.parseObject(getFileResult);
    }

    //删除cos远程文件
    //返回格式:{"code":0,"message":"SUCCESS","request_id":"NTllYWJhOGVfY2NhMzNiMGFfYWViOF9lNTc4YWM="}
    public JSONObject deleteFile(String cosFilePath) {

        DelFileRequest delFileRequest = new DelFileRequest(qCloudConfig.getCosBucketName(), cosFilePath);
        String delFileResult = cosClient.delFile(delFileRequest);
        logger.info("qcloud delFileResult: " + delFileResult);
        return JSON.parseObject(delFileResult);
    }

    //释放cos客户端
    public void releaseCosClient() {

        // 关闭释放资源
        cosClient.shutdown();
    }

}
