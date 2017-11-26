package com.highplace.biz.pm.domain.org;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.highplace.biz.pm.service.util.CommonUtils;
import com.highplace.biz.pm.service.util.excel.ExcelResources;
import com.highplace.biz.pm.service.util.json.DateJsonDeserializer;
import com.highplace.biz.pm.service.util.json.DateJsonSerializer;
import com.highplace.biz.pm.service.util.json.DateTimeJsonDeserializer;
import com.highplace.biz.pm.service.util.json.DateTimeJsonSerializer;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Employee {

    // ----- mybatis generator外新增的属性------ //

    private String deptName;   //员工所属的部门信息

    @ExcelResources(title=" 部门 ",order=2)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    //证件类型: 0:居民身份证 1:护照 2:港澳回乡证 3:台胞证 4:其他
    public static String transferIdentityTypeToDesc(int type) {

        if (type == 0) return "居民身份证";
        if (type == 1) return "护照";
        if (type == 2) return "港澳回乡证";
        if (type == 3) return "台胞证";
        if (type == 4) return "其他";
        return "未知";
    }

    //证件类型: 0:居民身份证 1:护照 2:港澳回乡证 3:台胞证 4:其他
    //说明转换成id
    public static int transferDescToIdentityType(String desc) {
        if (StringUtils.isNotEmpty(desc)) {
            if (desc.equals("居民身份证")) return 0;
            if (desc.equals("护照")) return 1;
            if (desc.equals("港澳回乡证")) return 2;
            if (desc.equals("台胞证")) return 3;
            if (desc.equals("其他")) return 4;
            return 0;
        }
        return 0;
    }

    //员工状态: 0:在岗 1:不在岗 2:离职
    public static String transferStatusToDesc(int status) {

        if (status == 0) return "在岗";
        if (status == 1) return "不在岗";
        if (status == 2) return "离职";
        return "其他";
    }

    //员工状态: 0:在岗 1:不在岗 2:离职  默认o
    //说明转换成id
    public static int transferDescToStatus(String statusDesc) {
        if (StringUtils.isNotEmpty(statusDesc)) {
            if (statusDesc.equals("在岗")) return 0;
            if (statusDesc.equals("不在岗")) return 1;
            if (statusDesc.equals("离职")) return 2;
            return 0;
        }
        return 0;
    }

    // ----- end ------ //

    private Long employeeId;

    private String productInstId;

    @NotNull
    private Long deptId;

    @NotNull
    @Length(min = 1)
    private String employeeName;

    @NotNull
    @Length(min = 1)
    private String phone;

    private String sysUsername;

    private String employeeCode;

    private String position;

    private Integer status;

    private String aliasName;

    private Integer identityType;

    private String identityNo;

    private String identPic;

    private String email;

    private String wechat;

    private String backupPhone1;

    private String backupPhone2;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private String gender;

    //@DateTimeFormat(pattern="yyyy-MM-dd")
    //@JsonSerialize(using = DateJsonSerializer.class)
    //@JsonDeserialize(using = DateJsonDeserializer.class)
    private Date entryDate;

    //@DateTimeFormat(pattern="yyyy-MM-dd")
    //@JsonSerialize(using = DateJsonSerializer.class)
    //@JsonDeserialize(using = DateJsonDeserializer.class)
    private Date leaveDate;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date modifyTime;

    private String remark;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @ExcelResources(title="姓名",order=1)
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    @ExcelResources(title=" 电话 ",order=4)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getSysUsername() {
        return sysUsername;
    }

    public void setSysUsername(String sysUsername) {
        this.sysUsername = sysUsername == null ? null : sysUsername.trim();
    }

    @ExcelResources(title="工号",order=6)
    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode == null ? null : employeeCode.trim();
    }

    @ExcelResources(title=" 岗位 ",order=3)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getStatus() {
        return status;
    }

    @ExcelResources(title="状态",order=11)
    @JsonIgnore
    public String getStatusDesc() {
        return Employee.transferStatusToDesc(getStatus());
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName == null ? null : aliasName.trim();
    }

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
    }

    //@ExcelResources(title="证件类型(居民身份证/护照/港澳回乡证/台胞证)",order=)
    @ExcelResources(title="证件类型",order=7)
    @JsonIgnore
    public String getIdentityTypeDesc(){
        return Employee.transferIdentityTypeToDesc(getIdentityType());
    }

    @ExcelResources(title="证件号码",order=8)
    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo == null ? null : identityNo.trim();
    }

    public String getIdentPic() {
        return identPic;
    }

    public void setIdentPic(String identPic) {
        this.identPic = identPic == null ? null : identPic.trim();
    }

    @ExcelResources(title="电子邮箱",order=12)
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

    @ExcelResources(title="备用电话",order=5)
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

    @ExcelResources(title="性别",order=9)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    @ExcelResources(title="入职日期",order=10)
    @JsonIgnore
    public String getFormatEntryDate() {
        return (entryDate!=null)? CommonUtils.getTimeString(entryDate, CommonUtils.FORMAT_DAY):null;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
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