package com.highplace.biz.pm.domain.ui;

public class EmployeeSearchBean extends PageBean {

    private Long deptId;         //所属部门ID
    private String position;     //员工岗位
    private String employeeName; //员工姓名
    private String phone;        //联系电话
    private Integer status;      //员工状态: 0:在岗 1:不在岗 2:离职

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeSearchBean{" +
                "deptId=" + deptId +
                ", position='" + position + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}' +
                super.toString();
    }
}
