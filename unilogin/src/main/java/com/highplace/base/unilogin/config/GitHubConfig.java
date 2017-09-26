package com.highplace.base.unilogin.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="unilogin.github")
public class GitHubConfig {

    private String clientid;
    private String clientsecret;
    private String callback;
    private String protected_resource_url;

    public void setProtected_resource_url(String protected_resource_url) {
        this.protected_resource_url = protected_resource_url;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public void setCallback(String callback) {
        this.callback = callback;
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

    public String getProtected_resource_url() {
        return protected_resource_url;
    }
}
