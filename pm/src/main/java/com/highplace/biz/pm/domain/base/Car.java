package com.highplace.biz.pm.domain.base;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Car {

    // ----- mybatis generator外新增的属性------ //
    private Relation relation;

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    //车位产权类型: 0:公共产权 1:自有产权
    public static String transferTypeToDesc(int type) {

        if (type == 0) return "公共产权";
        if (type == 1) return "自有产权";
        return "未知";
    }

    //车位产权类型: 0:公共产权 1:自有产权
    //说明转换成id
    public static int transferDescToType(String typeDesc) {
        if (StringUtils.isNotEmpty(typeDesc)) {
            if (typeDesc.equals("公共产权")) return 0;
            if (typeDesc.equals("自有产权")) return 1;
            return 0;
        }
        return 0;
    }

    // ----- end ------ //

    private Long carId;

    private String productInstId;

    private Long relationId;

    @NotNull
    @Length(min = 1)
    private String plateNo;

    private Integer type;

    private Integer chargeStatus;

    private String parkInfo;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo == null ? null : plateNo.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(Integer chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getParkInfo() {
        return parkInfo;
    }

    public void setParkInfo(String parkInfo) {
        this.parkInfo = parkInfo == null ? null : parkInfo.trim();
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