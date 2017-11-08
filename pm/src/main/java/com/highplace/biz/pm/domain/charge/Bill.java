package com.highplace.biz.pm.domain.charge;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Bill {

    // ----- mybatis generator外新增的属性------ //
    @NotNull
    private List<BillSubjectRel> billSubjectRelList;   //账单下的所有收费科目

    public List<BillSubjectRel> getBillSubjectRelList() {
        return billSubjectRelList;
    }

    public void setBillSubjectRelList(List<BillSubjectRel> billSubjectRelList) {
        this.billSubjectRelList = billSubjectRelList;
    }
    // ----- end ------ //

    private Long billId;

    private String productInstId;

    @NotNull
    private String billName;

    @NotNull
    private Integer billDay;

    @NotNull
    private Integer billCycle;

    @NotNull
    private Integer lastPayDay;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName == null ? null : billName.trim();
    }

    public Integer getBillDay() {
        return billDay;
    }

    public void setBillDay(Integer billDay) {
        this.billDay = billDay;
    }

    public Integer getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(Integer billCycle) {
        this.billCycle = billCycle;
    }

    public Integer getLastPayDay() {
        return lastPayDay;
    }

    public void setLastPayDay(Integer lastPayDay) {
        this.lastPayDay = lastPayDay;
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