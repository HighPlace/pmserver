package com.highplace.service.oauth.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id" )
    private Long userId;

    @NotNull
    @Column(name = "product_inst_id" )
    private String productInstId;  //用户一定属于某个产品实例

    @NotNull
    @Length(min = 3, max = 30)
    @Column(name = "username" )
    private String username;

    @Column(name = "mobile_no" )
    private String mobileNo;

    @Column(name = "email" )
    private String email;

    @Column(name = "wx_open_id" )
    private String wxOpenId;

    @Column(name = "password" )
    private String password;

    @Column(name = "credential_expired" )
    private Boolean credentialExpired;

    @Column(name = "account_expired" )
    private Boolean accountExpired;

    @Column(name = "account_locked" )
    private Boolean accountLocked;

    @Column(name = "enabled" )
    private Boolean enabled;

    @Column(name = "super_user_flag" )
    private Boolean superUserFlag;

    @Column(name = "create_time" )
    private Date createTime;

    @Column(name = "modify_time" )
    private Date modifyTime;

    @Column(name = "remark" )
    private String remark;

    private List<Role> roles;   //用户下的所有角色

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId == null ? null : wxOpenId.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Boolean getCredentialExpired() {
        return credentialExpired;
    }

    public void setCredentialExpired(Boolean credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getSuperUserFlag() {
        return superUserFlag;
    }

    public void setSuperUserFlag(Boolean superUserFlag) {
        this.superUserFlag = superUserFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}