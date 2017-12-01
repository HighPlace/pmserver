package com.highplace.biz.pm.domain.ui;

public class EmployeeSearchBean extends PageBean {

    private Long deptId;         //所属部门ID
    private String position;     //员工岗位
    private String employeeName; //员工姓名
    private String phone;        //联系电话
    private Integer status;      //员工状态: 0:在岗 1:不在岗 2:离职
    private String username;  //员工在it系统中的用户名,若有创建用户
    private Boolean hasSysUser;  //是否有平台账号


    public Boolean getHasSysUser() {
        return hasSysUser;
    }

    public void setHasSysUser(Boolean hasSysUser) {
        this.hasSysUser = hasSysUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
                ", username='" + username + '\'' +
                ", hasSysUser=" + hasSysUser +
                '}';
    }
}
