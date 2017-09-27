package com.highplace.service.oauth.domain;

import org.springframework.security.core.GrantedAuthority;

public class MyGrantedAuthority implements GrantedAuthority {

    private String resourceUrl;

    private String httpMethod;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public MyGrantedAuthority(String resourceUrl, String httpMethod) {
        this.resourceUrl = resourceUrl;
        this.httpMethod = httpMethod;
    }

    @Override
    public String getAuthority() {
        return this.resourceUrl + ";" + this.httpMethod;
    }
}
