package com.highplace.biz.pm.domain.ui;

public class ChargeSearchBean extends PageBean {

    private Long chargeId;       //出账ID
    private Long billId;         //账单类型ID
    private String billPeriod;   //账期,如 2017年10月
    private Integer status;          //状态:0:出账中 1:仪表数据导入完成 2:出账完成 3:收费中 4:收费完成

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getBillPeriod() {
        return billPeriod;
    }

    public void setBillPeriod(String billPeriod) {
        this.billPeriod = billPeriod;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }
}
