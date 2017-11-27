package com.highplace.biz.pm.domain.ui;

public class DepartmentSearchBean extends PageBean {

    private Long superiorDeptId;  //上级部门ID

    public Long getSuperiorDeptId() {
        return superiorDeptId;
    }

    public void setSuperiorDeptId(Long superiorDeptId) {
        this.superiorDeptId = superiorDeptId;
    }
}
