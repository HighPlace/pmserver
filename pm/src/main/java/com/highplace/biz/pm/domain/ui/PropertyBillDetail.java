package com.highplace.biz.pm.domain.ui;

import java.io.Serializable;
import java.util.List;

//基于房产的账单收费明细数据
public class PropertyBillDetail implements Serializable {


    private List<FeeDataTypeNull> feeDataTypeNullList;   //独立费用(包含服务工单)
    private List<FeeDataTypeCar> feeDataTypeCarList;    //车辆相关费用
    private List<FeeDataTypePropertyArea> feeDataTypePropertyAreaList;  //产权面积相关费用
    private List<FeeDataTypeMeter> feeDataTypeMeterList;  //仪表用量相关费用

    public List<FeeDataTypeNull> getFeeDataTypeNullList() {
        return feeDataTypeNullList;
    }

    public void setFeeDataTypeNullList(List<FeeDataTypeNull> feeDataTypeNullList) {
        this.feeDataTypeNullList = feeDataTypeNullList;
    }

    public List<FeeDataTypeCar> getFeeDataTypeCarList() {
        return feeDataTypeCarList;
    }

    public void setFeeDataTypeCarList(List<FeeDataTypeCar> feeDataTypeCarList) {
        this.feeDataTypeCarList = feeDataTypeCarList;
    }

    public List<FeeDataTypePropertyArea> getFeeDataTypePropertyAreaList() {
        return feeDataTypePropertyAreaList;
    }

    public void setFeeDataTypePropertyAreaList(List<FeeDataTypePropertyArea> feeDataTypePropertyAreaList) {
        this.feeDataTypePropertyAreaList = feeDataTypePropertyAreaList;
    }

    public List<FeeDataTypeMeter> getFeeDataTypeMeterList() {
        return feeDataTypeMeterList;
    }

    public void setFeeDataTypeMeterList(List<FeeDataTypeMeter> feeDataTypeMeterList) {
        this.feeDataTypeMeterList = feeDataTypeMeterList;
    }


    public static class FeeDataTypeNull implements Serializable{
        private String subjectName;   //收费科目名称
        private String period;        //收费时段
        private Double amount;        //收费金额
        private Double rate;          //收费倍率

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public Double getRate() {
            return rate;
        }

        public void setRate(Double rate) {
            this.rate = rate;
        }
    }

    public static class FeeDataTypeCar extends FeeDataTypeNull implements Serializable{
        private Integer carNum;
        private Double fee;

        public Integer getCarNum() {
            return carNum;
        }

        public void setCarNum(Integer carNum) {
            this.carNum = carNum;
        }

        public Double getFee() {
            return fee;
        }

        public void setFee(Double fee) {
            this.fee = fee;
        }
    }

    public static class FeeDataTypePropertyArea extends FeeDataTypeNull implements Serializable{

        private Double propertyArea;
        private Double fee;

        public Double getPropertyArea() {
            return propertyArea;
        }

        public void setPropertyArea(Double propertyArea) {
            this.propertyArea = propertyArea;
        }

        public Double getFee() {
            return fee;
        }

        public void setFee(Double fee) {
            this.fee = fee;
        }
    }

    public static class FeeDataTypeMeter extends FeeDataTypeNull implements Serializable{

        private Double feeLevelOne;

        private Double levelOneUsage;

        private Double feeLevelTwo;

        private Double levelTwoUsage;

        private Double feeLevelThree;

        private Double levelThreeUsage;

        public Double getFeeLevelOne() {
            return feeLevelOne;
        }

        public void setFeeLevelOne(Double feeLevelOne) {
            this.feeLevelOne = feeLevelOne;
        }

        public Double getLevelOneUsage() {
            return levelOneUsage;
        }

        public void setLevelOneUsage(Double levelOneUsage) {
            this.levelOneUsage = levelOneUsage;
        }

        public Double getFeeLevelTwo() {
            return feeLevelTwo;
        }

        public void setFeeLevelTwo(Double feeLevelTwo) {
            this.feeLevelTwo = feeLevelTwo;
        }

        public Double getLevelTwoUsage() {
            return levelTwoUsage;
        }

        public void setLevelTwoUsage(Double levelTwoUsage) {
            this.levelTwoUsage = levelTwoUsage;
        }

        public Double getFeeLevelThree() {
            return feeLevelThree;
        }

        public void setFeeLevelThree(Double feeLevelThree) {
            this.feeLevelThree = feeLevelThree;
        }

        public Double getLevelThreeUsage() {
            return levelThreeUsage;
        }

        public void setLevelThreeUsage(Double levelThreeUsage) {
            this.levelThreeUsage = levelThreeUsage;
        }
    }



}
