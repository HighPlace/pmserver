package com.highplace.service.oauth.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class User implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue
    @Column(name = "user_id" )
    private Long userId;

    //@NotNull  测试需要,暂时去掉
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

    private List<Action> actions;; //用户角色对应的所有可以操作的资源

    private List<GrantedAuthority> authorities;

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

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

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
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

    //mybatis内部在进行Java反射的时候出现报错:Illegal overloaded getter method with ambiguous type for property
    //因为Java会把Boolean类型的getter方法默认为is打头的或者是get打头的,Java都会认为是bean的属性封装，
    //那么在反射的时候，如果两个方法都有,Java就不知道该get哪个了,去掉其中一个即可
    /*
    public Boolean getEnabled() {
        return enabled;
    }
    */

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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", productInstId='" + productInstId + '\'' +
                ", username='" + username + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", wxOpenId='" + wxOpenId + '\'' +
                ", password='" + password + '\'' +
                ", credentialExpired=" + credentialExpired +
                ", accountExpired=" + accountExpired +
                ", accountLocked=" + accountLocked +
                ", enabled=" + enabled +
                ", superUserFlag=" + superUserFlag +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", remark='" + remark + '\'' +
                ", roles=" + roles +
                ", actions=" + actions +
                '}';
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }
}