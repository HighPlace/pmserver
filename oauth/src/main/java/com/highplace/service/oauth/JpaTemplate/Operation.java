package com.highplace.service.oauth.JpaTemplate;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Operation implements GrantedAuthority {
    @Id
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public Operation(){}

    public Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}