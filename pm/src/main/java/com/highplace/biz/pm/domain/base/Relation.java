package com.highplace.biz.pm.domain.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.highplace.biz.pm.service.util.json.DateJsonDeserializer;
import com.highplace.biz.pm.service.util.json.DateJsonSerializer;
import com.highplace.biz.pm.service.util.json.DateTimeJsonDeserializer;
import com.highplace.biz.pm.service.util.json.DateTimeJsonSerializer;
import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Relation {

    // ----- mybatis generator外新增的属性------ //
    private List<Car> carList;   //客户对应房产下的私家车信息
    private String propertyName; //zoneId + buildingId + unitId + roomId组成的房产名称

    public List<Car> getCarList() {
        return carList;
    }
    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    //客户和房产的关系类型: 0:业主 1:租户 2:其他
    public static String transferTypeToDesc(int type) {

        if (type == 0) return "业主";
        if (type == 1) return "租户";
        return "其他";
    }

    //客户和房产的关系类型: 0:业主 1:租户 2:其他
    //说明转换成id
    public static int transferDescToType(String typeDesc) {
        if (StringUtils.isNotEmpty(typeDesc)) {
            if (typeDesc.equals("业主")) return 0;
            if (typeDesc.equals("租户")) return 1;
            return 2;
        }
        return 2;
    }

// ----- end ------ //

    private Long relationId;

    private String productInstId;

    private Long customerId;

    @NotNull
    private Long propertyId;

    private Integer type;

    private Integer status;

    //@DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = DateJsonSerializer.class)
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date startDate;

    //@DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = DateJsonSerializer.class)
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date endDate;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date createTime;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @JsonDeserialize(using = DateTimeJsonDeserializer.class)
    private Date modifyTime;

    private String remark;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getProductInstId() {
        return productInstId;
    }

    public void setProductInstId(String productInstId) {
        this.productInstId = productInstId == null ? null : productInstId.trim();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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