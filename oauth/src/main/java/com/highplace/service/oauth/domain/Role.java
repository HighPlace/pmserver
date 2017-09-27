package com.highplace.service.oauth.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id" )
    private Long roleId;

    @Column(name = "product_inst_id" )
    private String productInstId;

    @Column(name = "role_name" )
    private String roleName;

    @Column(name = "create_time" )
    private Date createTime;

    @Column(name = "modify_time" )
    private Date modifyTime;

    @Column(name = "super_role_flag" )
    private Boolean superRoleFlag;

    @Column(name = "remark" )
    private String remark;

    private List<Action> actions;   //角色下的所有操作

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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

    public Boolean getSuperRoleFlag() {
        return superRoleFlag;
    }

    public void setSuperRoleFlag(Boolean superRoleFlag) {
        this.superRoleFlag = superRoleFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}