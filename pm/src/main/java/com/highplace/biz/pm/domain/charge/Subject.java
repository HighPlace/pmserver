package com.highplace.biz.pm.domain.charge;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.highplace.biz.pm.service.util.json.DateTimeJsonDeserializer;
import com.highplace.biz.pm.service.util.json.DateTimeJsonSerializer;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Subject {
    private Long subjectId;

    private String productInstId;

    @NotNull
    @Length(min = 1)
    private String subjectName;

    private Integer chargeCycle;

    private Integer cycleFlag;

    private Double lateFee;

    private Double rate;

    @NotNull
    private Double feeLevelOne;

    private Double levelOneToplimit;

    private Double feeLevelTwo;

    private Double levelTwoToplimit;

    private Double feeLevelThree;

    private Double levelThreeToplimit;

    @NotNull
    private String feeDataUnit;

    private Integer feeDataType;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date modifyTime;

    private String remark;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public Integer getChargeCycle() {
        return chargeCycle;
    }

    public void setChargeCycle(Integer chargeCycle) {
        this.chargeCycle = chargeCycle;
    }

    public Integer getCycleFlag() {
        return cycleFlag;
    }

    public void setCycleFlag(Integer cycleFlag) {
        this.cycleFlag = cycleFlag;
    }

    public Double getLateFee() {
        return lateFee;
    }

    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getFeeLevelOne() {
        return feeLevelOne;
    }

    public void setFeeLevelOne(Double feeLevelOne) {
        this.feeLevelOne = feeLevelOne;
    }

    public Double getLevelOneToplimit() {
        return levelOneToplimit;
    }

    public void setLevelOneToplimit(Double levelOneToplimit) {
        this.levelOneToplimit = levelOneToplimit;
    }

    public Double getFeeLevelTwo() {
        return feeLevelTwo;
    }

    public void setFeeLevelTwo(Double feeLevelTwo) {
        this.feeLevelTwo = feeLevelTwo;
    }

    public Double getLevelTwoToplimit() {
        return levelTwoToplimit;
    }

    public void setLevelTwoToplimit(Double levelTwoToplimit) {
        this.levelTwoToplimit = levelTwoToplimit;
    }

    public Double getFeeLevelThree() {
        return feeLevelThree;
    }

    public void setFeeLevelThree(Double feeLevelThree) {
        this.feeLevelThree = feeLevelThree;
    }

    public Double getLevelThreeToplimit() {
        return levelThreeToplimit;
    }

    public void setLevelThreeToplimit(Double levelThreeToplimit) {
        this.levelThreeToplimit = levelThreeToplimit;
    }

    public String getFeeDataUnit() {
        return feeDataUnit;
    }

    public void setFeeDataUnit(String feeDataUnit) {
        this.feeDataUnit = feeDataUnit == null ? null : feeDataUnit.trim();
    }

    public Integer getFeeDataType() {
        return feeDataType;
    }

    public void setFeeDataType(Integer feeDataType) {
        this.feeDataType = feeDataType;
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