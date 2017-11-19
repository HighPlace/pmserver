package com.highplace.biz.pm.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    //获取临时token，用于从页面直接上传文件到阿里云oss
    @RequestMapping(path = "/aliyun/ossTempToken", method = RequestMethod.GET)
    public Map<String, String> getTempTokenFromSTS() throws Exception {

        String roleSessionName = "aliyun-oss-temp-token"; //同时作为redis的key,aliyun规定2-32个字符,参考https://help.aliyun.com/document_detail/28763.html

        //先从redis获取
        ValueOperations<Object, HashMap<String, String>> valueOperations = redisTemplate.opsForValue();
        if(redisTemplate.hasKey(roleSessionName)){
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
                respMap.put("status", "200");
                respMap.put("AccessKeyId", stsResponse.getCredentials().getAccessKeyId());
                respMap.put("AccessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
                respMap.put("SecurityToken", stsResponse.getCredentials().getSecurityToken());
                respMap.put("Expiration", stsResponse.getCredentials().getExpiration());

                //写入cache
                valueOperations.set(roleSessionName, respMap);
                redisTemplate.expire(roleSessionName, durationSeconds - 60 , TimeUnit.SECONDS); //cache有效期,设置为比aliyun oss少一分钟

                return respMap;
            } catch (ClientException e) {
                HashMap<String, String> respMap = new HashMap<>();
                respMap.put("status", e.getErrCode());
                respMap.put("AccessKeyId", "");
                respMap.put("AccessKeySecret", "");
                respMap.put("SecurityToken", "");
                respMap.put("Expiration", "");
                return respMap;
            }
        }
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
