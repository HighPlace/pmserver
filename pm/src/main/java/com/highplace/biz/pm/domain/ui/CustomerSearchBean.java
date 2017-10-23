package com.highplace.biz.pm.domain.ui;

public class CustomerSearchBean extends PropertySearchBean {

    private String customerName; //客户名称
    private String phone;        //客户电话
    private String plateNo;      //车牌号

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    @Override
    public String toString() {
        return "CustomerSearchBean{" +
                "customerName='" + customerName + '\'' +
                ", phone='" + phone + '\'' +
                ", plateNo='" + plateNo + '\'' +
                '}' +
                super.toString();
    }
}
