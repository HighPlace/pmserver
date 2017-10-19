package com.highplace.biz.pm.domain.ui;

public class PageBean {

    private Integer pageNum;   //页数
    private Integer pageSize;  //单页数据条数
    private String sortField;  //排序字段
    private String sortType;   //asc升序  desc降序

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sortField='" + sortField + '\'' +
                ", sortType='" + sortType + '\'' +
                '}';
    }
}
