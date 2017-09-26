package com.highplace.base.unilogin.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String github_openid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getGithub_openid() {
        return github_openid;
    }

    public void setGithub_openid(String github_openid) {
        this.github_openid = github_openid == null ? null : github_openid.trim();
    }
}


