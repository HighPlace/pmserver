package com.highplace.service.oauth.domain;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class UserRole implements Serializable, GrantedAuthority {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAuthority(){
        return getName();
    }
}