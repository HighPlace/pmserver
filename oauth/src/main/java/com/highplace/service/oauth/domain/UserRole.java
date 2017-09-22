package com.highplace.service.oauth.domain;

import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {

    private String role;

    public UserRole(String role){
        super();
        this.role = role;

    }
    public String getAuthority(){
        return role;
    }
}