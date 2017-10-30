package com.highplace.biz.pm.domain.ui;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RequestSearchBean extends PageBean {

    private String type;    //服务工单大类,可以自定义,报事/报修/投诉为系统缺省的三个

    private String subType; //服务工单子类,可以自定义,如报修下面包含排水故障/空调故障等

    private Integer source; //服务工单来源: 0:未知 1:客服电话 2:公众号 3:客户上门 4:内部

    private Integer status; //服务工单状态: 0:待指派 1:待处理 2:处理中 3:处理完成 4:评价完成

    private Integer priority; //服务优先级: 1-5从低到高

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date requestDateFrom;  //服务请求时间段

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date requestDateTo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getRequestDateFrom() {
        return requestDateFrom;
    }

    public void setRequestDateFrom(Date requestDateFrom) {
        this.requestDateFrom = requestDateFrom;
    }

    public Date getRequestDateTo() {
        return requestDateTo;
    }

    public void setRequestDateTo(Date requestDateTo) {
        this.requestDateTo = requestDateTo;
    }

    @Override
    public String toString() {
        return "RequestSearchBean{" +
                "type='" + type + '\'' +
                ", subType='" + subType + '\'' +
                ", source=" + source +
                ", status=" + status +
                ", priority=" + priority +
                ", requestDateFrom=" + requestDateFrom +
                ", requestDateTo=" + requestDateTo +
                '}' +
                super.toString();
    }
}
