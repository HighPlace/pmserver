package com.highplace.service.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="unilogin.wechat")
public class WechatConfig {

    private String clientid;
    private String clientsecret;
    private String callback;
    private String token;   //用于微信服务器请求的Token验证

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClientid() {
        return clientid;
    }

    public String getClientsecret() {
        return clientsecret;
    }

    public String getCallback() {
        return callback;
    }

    public String getToken() {
        return token;
    }
}