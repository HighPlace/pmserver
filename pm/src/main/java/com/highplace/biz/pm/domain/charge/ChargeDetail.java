package com.highplace.biz.pm.domain.charge;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.highplace.biz.pm.service.util.excel.ExcelResources;
import com.highplace.biz.pm.service.util.json.DateTimeJsonDeserializer;
import com.highplace.biz.pm.service.util.json.DateTimeJsonSerializer;

import java.util.Date;

public class ChargeDetail {


    //支付状态:0:收费中 1:欠费 2:已缴费
    public static String transferPayStatusToDesc(int payStatus) {

        if (payStatus == 0) return "收费中";
        if (payStatus == 1) return "欠费";
        if (payStatus == 2) return "已缴费";
        return "未知";
    }

    //缴费方式:0:银行托收 1:微信缴费
    public static String transferPayTypeToDesc(int payType) {

        if (payType == 0) return "银行托收";
        if (payType == 1) return "微信缴费";
        return "未知";
    }

    @ExcelResources(title="支付状态",order=6)
    public String getPayStatusDesc(){
        return transferPayStatusToDesc(getPayStatus());
    }

    @ExcelResources(title="缴费方式",order=5)
    public String getPayTypeDesc(){
        return transferPayTypeToDesc(getPayType());
    }

    private Long detailId;

    private String productInstId;

    private Long chargeId;

    private Long propertyId;

    private Double amount;

    private Integer payStatus;

    private Integer payType;

    private String payId;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date modifyTime;

    private Long billId;

    private String billName;

    private String propertyName;

    private String billPeriod;

    private String remark;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

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

    @ExcelResources(title="金额",order=4)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
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

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    @ExcelResources(title="账单类别",order=2)
    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName == null ? null : billName.trim();
    }

    @ExcelResources(title="房产",order=3)
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    @ExcelResources(title="账期",order=1)
    public String getBillPeriod() {
        return billPeriod;
    }

    public void setBillPeriod(String billPeriod) {
        this.billPeriod = billPeriod == null ? null : billPeriod.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}