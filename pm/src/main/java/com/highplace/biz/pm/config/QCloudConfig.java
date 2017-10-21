package com.highplace.biz.pm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="qcloud.api")
public class QCloudConfig {

    private String appId;
    private String SecretId;
    private String secretKey;
    private String cosBucketName;

    public String getCosBucketName() {
        return cosBucketName;
    }

    public void setCosBucketName(String cosBucketName) {
        this.cosBucketName = cosBucketName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretId() {
        return SecretId;
    }

    public void setSecretId(String secretId) {
        SecretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
