package com.highplace.biz.pm.domain.ui;

public class PropertySearchBean extends PageBean {


    private String zoneId;
    private String buildingId;
    private String unitId;
    private String roomId;
    private Integer status;

    public String getZoneId() {
        return zoneId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getRoomId() {
        return roomId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PropertySearchBean{" +
                "zoneId='" + zoneId + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", unitId='" + unitId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", status=" + status +
                '}' +
                super.toString();
    }
}
