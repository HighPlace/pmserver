package com.highplace.biz.pm.domain.ui;

import com.highplace.biz.pm.domain.base.Car;
import com.highplace.biz.pm.domain.base.Customer;
import com.highplace.biz.pm.domain.base.Relation;
import com.highplace.biz.pm.service.util.ExcelResources;

public class CustomerExcelBean {

    public Customer customer;
    public Relation relation;
    public Car car;

    public CustomerExcelBean() {
        this.customer = new Customer();
        this.relation = new Relation();
        this.car = new Car();
    }

    public CustomerExcelBean(Customer customer) {
        this.customer = customer;
        this.relation = new Relation();
        this.car = new Car();
    }

    public CustomerExcelBean(Customer customer, Relation relation) {
        this.customer = customer;
        this.relation = relation;
        this.car = new Car();
    }

    public CustomerExcelBean(Customer customer, Relation relation, Car car) {
        this.customer = customer;
        this.relation = relation;
        this.car = car;
    }

    //@ExcelResources(title="房产名(分区+楼号+单元+房号)",order=1)
    @ExcelResources(title="房产名",order=1)
    public String getPropertyName() {
        return relation.getPropertyName();
    }

    //@ExcelResources(title="客户类型(业主/租户/其他)",order=2)
    @ExcelResources(title="客户类型",order=2)
    public String getRelationType() {
        return (relation.getType()!=null) ? Relation.transferTypeToDesc(relation.getType()) : null;
    }

    @ExcelResources(title="客户姓名",order=3)
    public String getCustomerName() {
        return customer.getCustomerName();
    }

    //@ExcelResources(title="证件类型(居民身份证/护照/港澳回乡证/台胞证)",order=4)
    @ExcelResources(title="证件类型",order=4)
    public String getIdentityTypeDesc(){
        return Customer.transferIdentityTypeToDesc(customer.getIdentityType());
    }

    @ExcelResources(title="证件号码",order=5)
    public String getIdentityNo() {
        return customer.getIdentityNo();
    }

    @ExcelResources(title="联系电话",order=6)
    public String getPhone() {
        return customer.getPhone();
    }

    @ExcelResources(title="备用电话",order=7)
    public String getBackupPhone1() {
        return customer.getBackupPhone1();
    }

    @ExcelResources(title="电子邮箱地址",order=8)
    public String getEmail() {
        return customer.getEmail();
    }

    @ExcelResources(title="国籍",order=9)
    public String getNation() {
        return customer.getNation();
    }

    //@ExcelResources(title="性别(男/女)",order=10)
    @ExcelResources(title="性别",order=10)
    public String getGender() {
        return customer.getGender();
    }

    @ExcelResources(title="车牌号",order=11)
    public String getPlateNo() {
        return car.getPlateNo();
    }

    @ExcelResources(title="车位信息",order=12)
    public String getParkInfo() {
        return car.getParkInfo();
    }

    //@ExcelResources(title="车位类型(公共产权/自有产权)",order=13)
    @ExcelResources(title="车位产权",order=13)
    public String getParkType() {
        return (car.getType() !=null) ? Car.transferTypeToDesc(car.getType()) : null;
    }

}
