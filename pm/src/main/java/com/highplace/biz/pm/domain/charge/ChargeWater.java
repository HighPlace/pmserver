package com.highplace.biz.pm.domain.charge;

import java.util.Date;

public class ChargeWater {
    private String productInstId;

    private Long chargeId;

    private Long propertyId;

    private Integer feeDataType;

    private String propertyName;

    private Date beginDate;

    private Date endDate;

    private Double beginUsage;

    private Double endUsage;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getFeeDataType() {
        return feeDataType;
    }

    public void setFeeDataType(Integer feeDataType) {
        this.feeDataType = feeDataType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getBeginUsage() {
        return beginUsage;
    }

    public void setBeginUsage(Double beginUsage) {
        this.beginUsage = beginUsage;
    }

    public Double getEndUsage() {
        return endUsage;
    }

    public void setEndUsage(Double endUsage) {
        this.endUsage = endUsage;
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