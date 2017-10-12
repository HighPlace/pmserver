package com.highplace.service.oauth.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Module implements Serializable {

    @Id
    @GeneratedValue
    private Long moduleId;
    private String moduleName;
    private Date releaseTime;
    private Date cancleTime;
    private String version;
    private String remark;
    private Date createTime;
    private Date modifyTime;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getCancleTime() {
        return cancleTime;
    }

    public void setCancleTime(Date cancleTime) {
        this.cancleTime = cancleTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "Module{" +
                "moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                ", releaseTime=" + releaseTime +
                ", cancleTime=" + cancleTime +
                ", version='" + version + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
