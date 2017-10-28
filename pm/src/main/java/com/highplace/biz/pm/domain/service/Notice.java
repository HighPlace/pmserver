package com.highplace.biz.pm.domain.service;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Notice {
    private Long noticeId;

    private String productInstId;

    @NotNull
    private String title;

    @NotNull
    private String type;

    private Integer status;

    private Date publishDate;

    private Date validDate;

    private String publisher;

    private String approver;

    private String attachmentName;

    private String attachmentLink;

    private String specifyZoneIdList;

    private String specifyBuildingIdList;

    private String specifyUnitIdList;

    private String specifyRoomIdList;

    private Date createTime;

    private Date modifyTime;

    @NotNull
    private String content;

    private String remark;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver == null ? null : approver.trim();
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName == null ? null : attachmentName.trim();
    }

    public String getAttachmentLink() {
        return attachmentLink;
    }

    public void setAttachmentLink(String attachmentLink) {
        this.attachmentLink = attachmentLink == null ? null : attachmentLink.trim();
    }

    public String getSpecifyZoneIdList() {
        return specifyZoneIdList;
    }

    public void setSpecifyZoneIdList(String specifyZoneIdList) {
        this.specifyZoneIdList = specifyZoneIdList == null ? null : specifyZoneIdList.trim();
    }

    public String getSpecifyBuildingIdList() {
        return specifyBuildingIdList;
    }

    public void setSpecifyBuildingIdList(String specifyBuildingIdList) {
        this.specifyBuildingIdList = specifyBuildingIdList == null ? null : specifyBuildingIdList.trim();
    }

    public String getSpecifyUnitIdList() {
        return specifyUnitIdList;
    }

    public void setSpecifyUnitIdList(String specifyUnitIdList) {
        this.specifyUnitIdList = specifyUnitIdList == null ? null : specifyUnitIdList.trim();
    }

    public String getSpecifyRoomIdList() {
        return specifyRoomIdList;
    }

    public void setSpecifyRoomIdList(String specifyRoomIdList) {
        this.specifyRoomIdList = specifyRoomIdList == null ? null : specifyRoomIdList.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}