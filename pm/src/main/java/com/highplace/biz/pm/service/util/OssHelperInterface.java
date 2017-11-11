package com.highplace.biz.pm.service.util;

import com.alibaba.fastjson.JSONObject;

public interface OssHelperInterface {

    //释放cos客户端
    void releaseCosClient() ;

    //上传文件到cos
    //返回格式:{"code":0,"message":"SUCCESS"}
    JSONObject uploadFile(String bucketName, String cosFilePath, String localFilePath);

    //下载文件到本地
    //返回格式:{"code":0,"message":"SUCCESS"}
    JSONObject getFile(String bucketName, String cosFilePath, String localFilePath);

    //删除cos远程文件
    //返回格式:{"code":0,"message":"SUCCESS"}
    JSONObject deleteFile(String bucketName, String cosFilePath) ;

    //创建目录，目录必须以/结尾，如"/sample_folder/subfolder/"
    void createFolder(String bucketName, String cosFolderPath);

    //生成文件下载URL,文件名不能以/结尾,如：/pic/test.jpg
    String getDownLoadUrl(String bucketName, String cosFilePath, String sourceURL) ;
}
