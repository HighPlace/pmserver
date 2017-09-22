package com.highplace.service.oauth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;

public class UserRole implements Serializable, GrantedAuthority {

    SimpleGrantedAuthority a;

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