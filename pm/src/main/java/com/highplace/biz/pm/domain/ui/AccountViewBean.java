package com.highplace.biz.pm.domain.ui;

import com.highplace.biz.pm.domain.system.Account;

public class AccountViewBean extends Account{

    private String employeeName;
    private String deptName;
    private String position;
    private Integer status;  //员工状态: 0:在岗 1:不在岗 2:离职

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
