package com.highplace.service.oauth.domain;

public class UserView {

    private String productInstId;
    private Long userId;
    private String username;

    public UserView(String productInstId, Long userId, String username) {
        this.productInstId = productInstId;
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserView{" +
                "productInstId='" + productInstId + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
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
}
