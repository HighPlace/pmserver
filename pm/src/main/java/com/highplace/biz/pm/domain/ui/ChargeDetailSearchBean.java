package com.highplace.biz.pm.domain.ui;

import java.util.HashMap;
import java.util.Map;

public class ChargeDetailSearchBean extends ChargeSearchBean {

    private Integer payStatus;
    private Integer vendor;

    public Integer getVendor() {
        return vendor;
    }

    public void setVendor(Integer vendor) {
        this.vendor = vendor;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Map<String,Object> toMap(){
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("payStatus", getPayStatus());
        objectMap.put("chargeId", getChargeId());
        objectMap.put("billId", getBillId());
        objectMap.put("billPeriod", getBillPeriod());
        objectMap.put("status", getStatus());
        return objectMap;
    }
}
