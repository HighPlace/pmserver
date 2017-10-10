package com.highplace.service.oauth.domain;

public class UserView {

    private String productInstId;
    private Long userId;
    private String username;
    private String wxOpenId;
    private String password;

    public UserView(String productInstId, Long userId, String username, String wxOpenId, String password) {
        this.productInstId = productInstId;
        this.userId = userId;
        this.username = username;
        this.wxOpenId = wxOpenId;
        this.password = password;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserView{" +
                "productInstId='" + productInstId + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", wxOpenId='" + wxOpenId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
