package com.highplace.biz.pm.domain.base;

import java.util.Date;
import java.util.List;

public class Customer {

    // ----- mybatis generator外新增的属性------ //
    private List<Relation> relationList;   //客户名下的所有房产关系

    public List<Relation> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<Relation> relationList) {
        this.relationList = relationList;
    }
    // ----- end ------ //


    private Long customerId;

    private String productInstId;

    private String customerName;

    private Integer identityType;

    private String identityNo;

    private String phone;

    private String aliasName;

    private String email;

    private String wechat;

    private String lang;

    private String nation;

    private String gender;

    private Date birth;

    private Date identStartDate;

    private Date identEndDate;

    private String identPic;

    private String backupPhone1;

    private String backupPhone2;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo == null ? null : identityNo.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName == null ? null : aliasName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang == null ? null : lang.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getIdentStartDate() {
        return identStartDate;
    }

    public void setIdentStartDate(Date identStartDate) {
        this.identStartDate = identStartDate;
    }

    public Date getIdentEndDate() {
        return identEndDate;
    }

    public void setIdentEndDate(Date identEndDate) {
        this.identEndDate = identEndDate;
    }

    public String getIdentPic() {
        return identPic;
    }

    public void setIdentPic(String identPic) {
        this.identPic = identPic == null ? null : identPic.trim();
    }

    public String getBackupPhone1() {
        return backupPhone1;
    }

    public void setBackupPhone1(String backupPhone1) {
        this.backupPhone1 = backupPhone1 == null ? null : backupPhone1.trim();
    }

    public String getBackupPhone2() {
        return backupPhone2;
    }

    public void setBackupPhone2(String backupPhone2) {
        this.backupPhone2 = backupPhone2 == null ? null : backupPhone2.trim();
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName == null ? null : emergencyContactName.trim();
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone == null ? null : emergencyContactPhone.trim();
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