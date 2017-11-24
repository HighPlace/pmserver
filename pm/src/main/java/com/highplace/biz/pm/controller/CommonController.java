package com.highplace.biz.pm.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.highplace.biz.pm.config.AliyunConfig;
import com.highplace.biz.pm.service.util.cloud.UploadDownloadTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singletonMap;

@RestController
public class CommonController {

    public static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    public static final String STS_API_VERSION = "2015-04-01";

    @Value("${aliyun.api.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.api.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.api.oss.roleArn}")
    private String roleArn;

    @Value("${aliyun.api.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.api.oss.bucket}")
    private String bucket;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AliyunConfig aliyunConfig;

    //获取临时token，用于从页面直接上传文件到阿里云oss
    @RequestMapping(path = "/aliyun/ossTempToken", method = RequestMethod.GET)
    public Map<String, String> getTempTokenFromAliyunSTS() throws Exception {

        String roleSessionName = "aliyun-oss-temp-token-forall"; //同时作为redis的key,aliyun规定2-32个字符,参考https://help.aliyun.com/document_detail/28763.html

        //先从redis获取
        ValueOperations<Object, HashMap<String, String>> valueOperations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(roleSessionName)) {
            return valueOperations.get(roleSessionName);
        } else {
            long durationSeconds = 900; //有效期
            String policy = "{\n" +
                    "  \"Statement\": [\n" +
                    "    {\n" +
                    "      \"Action\": [\n" +
                    "        \"oss:GetObject\",\n" +
                    "        \"oss:PutObject\",\n" +
                    "        \"oss:DeleteObject\",\n" +
                    "        \"oss:ListParts\",\n" +
                    "        \"oss:AbortMultipartUpload\",\n" +
                    "        \"oss:ListObjects\"\n" +
                    "      ],\n" +
                    "      \"Effect\": \"Allow\",\n" +
                    "      \"Resource\": [\n" +
                    "        \"acs:oss:*:*:pmugc/*\",\n" +
                    "        \"acs:oss:*:*:pmugc\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"Version\": \"1\"\n" +
                    "}";
            ProtocolType protocolType = ProtocolType.HTTPS;
            try {
                final AssumeRoleResponse stsResponse = assumeRole(accessKeyId, accessKeySecret, roleArn, roleSessionName,
                        policy, protocolType, durationSeconds);
                HashMap<String, String> respMap = new HashMap<>();
                respMap.put("accessKeyId", stsResponse.getCredentials().getAccessKeyId());
                respMap.put("accessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
                respMap.put("stsToken", stsResponse.getCredentials().getSecurityToken());
                respMap.put("endpoint", endpoint);
                respMap.put("bucket", bucket);
                //写入cache
                valueOperations.set(roleSessionName, respMap);
                redisTemplate.expire(roleSessionName, durationSeconds - 60, TimeUnit.SECONDS); //cache有效期,设置为比aliyun oss少一分钟
                return respMap;
            } catch (ClientException e) {
                logger.error("aliyun sts error:{}, errorMsg:{}", e.getErrCode(), e.getErrMsg());
                throw new Exception("aliyun sts error:" + e.getErrCode() + ":" + e.getErrMsg());
            }
        }
    }

    //获取批量导入的模板文件, entity支持property/customer/employee
    @RequestMapping(path = "/aliyun/sampleUrl/{entity}", method = RequestMethod.GET)
    public Map<String, Object> getSampleUrlFromAliyun(@PathVariable String entity) throws Exception {

        String cosFilePath = "sample/" + entity + "-sample.xls";
        String fileUrl;
        if(redisTemplate.hasKey(cosFilePath)){
            fileUrl = (String)redisTemplate.opsForValue().get(cosFilePath);
        } else {
            fileUrl = UploadDownloadTool.getDownloadUrlFromAliyun(aliyunConfig, null, cosFilePath);
            if(fileUrl != null) {
                redisTemplate.opsForValue().set(cosFilePath, fileUrl, 55, TimeUnit.MINUTES); //下载url有效期是1小时,所以cache过期设置为55分钟
            }
        }
        return Collections.<String, Object>singletonMap("fileUrl",
                fileUrl);

    }

    protected AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
                                            String roleSessionName, String policy, ProtocolType protocolType, long durationSeconds) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);

            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);

            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);

            return response;
        } catch (ClientException e) {
            throw e;
        }
    }
}
