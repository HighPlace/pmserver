package com.highplace.biz.pm.domain.system;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Account {
    //employee相关信息
    private String employeeName;
    private String deptName;
    private String position;
    private Integer status;  //员工状态: 0:在岗 1:不在岗 2:离职

    //user相关信息
    private Long userId;
    private String productInstId;
    @NotNull
    private String username;
    private String mobileNo;  //可以修改
    private String email;     //可以修改
    private String wxOpenId;
    private String password;  //可以修改
    private Boolean credentialExpired;
    private Boolean accountExpired;
    private Boolean accountLocked; //可以修改
    private Boolean enabled;
    private Boolean superUserFlag;
    private String remark;
    @NotNull
    private List<Role> roles; //可以修改
    private Date createTime;
    private Date modifyTime;

    public static class Role{
        private Long roleId;
        private String roleName;

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        this.productInstId = productInstId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Account{" +
                "employeeName='" + employeeName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", position='" + position + '\'' +
                ", status=" + status +
                ", userId=" + userId +
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
                ", remark='" + remark + '\'' +
                ", roles=" + roles +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
