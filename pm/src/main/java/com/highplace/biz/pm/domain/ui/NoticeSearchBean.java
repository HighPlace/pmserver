package com.highplace.biz.pm.domain.ui;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NoticeSearchBean extends PageBean {

    private String title;

    private String type;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date publishDateFrom;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date publishDateTo;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getPublishDateFrom() {
        return publishDateFrom;
    }

    public void setPublishDateFrom(Date publishDateFrom) {
        this.publishDateFrom = publishDateFrom;
    }

    public Date getPublishDateTo() {
        return publishDateTo;
    }

    public void setPublishDateTo(Date publishDateTo) {
        this.publishDateTo = publishDateTo;
    }

    @Override
    public String toString() {
        return "NoticeSearchBean{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", publishDateFrom=" + publishDateFrom +
                ", publishDateTo=" + publishDateTo +
                ", status=" + status +
                '}' +
                super.toString();
    }
}
