package com.highplace.biz.pm.domain.service;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Request {
    private Long requestId;

    private String productInstId;

    @NotNull
    @Length(min = 1)
    private String type;

    @NotNull
    @Length(min = 1)
    private String subType;

    private Integer source;

    private Integer status;

    private String attachment1;

    private String attachment2;

    private String propertyId;

    private String submitter;

    private String contactInfo;

    private Integer priority;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date assignTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date acceptTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date finishTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date rateTime;

    private Long dealEmployeeId;

    private String dealDesc;

    private Integer rateLevel;

    private Integer rateNum;

    private String rateAttachment;

    private String rateUsername;

    private Date createTime;

    private Date modifyTime;

    @NotNull
    @Length(min = 1)
    private String content;

    private String rateDesc;

    private String remark;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType == null ? null : subType.trim();
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1 == null ? null : attachment1.trim();
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2 == null ? null : attachment2.trim();
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId == null ? null : propertyId.trim();
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter == null ? null : submitter.trim();
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo == null ? null : contactInfo.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Date assignTime) {
        this.assignTime = assignTime;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getRateTime() {
        return rateTime;
    }

    public void setRateTime(Date rateTime) {
        this.rateTime = rateTime;
    }

    public Long getDealEmployeeId() {
        return dealEmployeeId;
    }

    public void setDealEmployeeId(Long dealEmployeeId) {
        this.dealEmployeeId = dealEmployeeId;
    }

    public String getDealDesc() {
        return dealDesc;
    }

    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc == null ? null : dealDesc.trim();
    }

    public Integer getRateLevel() {
        return rateLevel;
    }

    public void setRateLevel(Integer rateLevel) {
        this.rateLevel = rateLevel;
    }

    public Integer getRateNum() {
        return rateNum;
    }

    public void setRateNum(Integer rateNum) {
        this.rateNum = rateNum;
    }

    public String getRateAttachment() {
        return rateAttachment;
    }

    public void setRateAttachment(String rateAttachment) {
        this.rateAttachment = rateAttachment == null ? null : rateAttachment.trim();
    }

    public String getRateUsername() {
        return rateUsername;
    }

    public void setRateUsername(String rateUsername) {
        this.rateUsername = rateUsername == null ? null : rateUsername.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRateDesc() {
        return rateDesc;
    }

    public void setRateDesc(String rateDesc) {
        this.rateDesc = rateDesc == null ? null : rateDesc.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}