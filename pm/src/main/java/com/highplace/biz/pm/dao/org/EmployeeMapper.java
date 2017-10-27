package com.highplace.biz.pm.dao.org;

import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.org.EmployeeExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface EmployeeMapper {

    // ----- mybatis generator外新增的属性------ //

    @SelectProvider(type=EmployeeSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
            @Result(column="employee_id", property="employeeId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
            @Result(column="dept_id", property="deptId", jdbcType=JdbcType.BIGINT),
            @Result(column="employee_name", property="employeeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="sys_username", property="sysUsername", jdbcType=JdbcType.VARCHAR),
            @Result(column="employee_code", property="employeeCode", jdbcType=JdbcType.VARCHAR),
            @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
            @Result(column="identity_type", property="identityType", jdbcType=JdbcType.INTEGER),
            @Result(column="identity_no", property="identityNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="ident_pic", property="identPic", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="wechat", property="wechat", jdbcType=JdbcType.VARCHAR),
            @Result(column="backup_phone_1", property="backupPhone1", jdbcType=JdbcType.VARCHAR),
            @Result(column="backup_phone_2", property="backupPhone2", jdbcType=JdbcType.VARCHAR),
            @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
            @Result(column="emergency_contact_phone", property="emergencyContactPhone", jdbcType=JdbcType.VARCHAR),
            @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
            @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.DATE),
            @Result(column="leave_date", property="leaveDate", jdbcType=JdbcType.DATE),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="dept_id", property="deptName", one=@One(select="com.highplace.biz.pm.dao.org.DepartmentMapper.selectDeptNameByPrimaryKey"))
    })
    List<Employee> selectByExampleWithBLOBsWithDeptName(EmployeeExample example);

    // ----- end ------ //


    @SelectProvider(type=EmployeeSqlProvider.class, method="countByExample")
    long countByExample(EmployeeExample example);

    @DeleteProvider(type=EmployeeSqlProvider.class, method="deleteByExample")
    int deleteByExample(EmployeeExample example);

    @Delete({
        "delete from t_employee",
        "where employee_id = #{employeeId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long employeeId);

    @Insert({
        "insert into t_employee (product_inst_id, dept_id, ",
        "employee_name, phone, ",
        "sys_username, employee_code, ",
        "position, status, ",
        "alias_name, identity_type, ",
        "identity_no, ident_pic, ",
        "email, wechat, backup_phone_1, ",
        "backup_phone_2, emergency_contact_name, ",
        "emergency_contact_phone, gender, ",
        "entry_date, leave_date, ",
        "create_time, modify_time, ",
        "remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{deptId,jdbcType=BIGINT}, ",
        "#{employeeName,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
        "#{sysUsername,jdbcType=VARCHAR}, #{employeeCode,jdbcType=VARCHAR}, ",
        "#{position,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
        "#{aliasName,jdbcType=VARCHAR}, #{identityType,jdbcType=INTEGER}, ",
        "#{identityNo,jdbcType=VARCHAR}, #{identPic,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{wechat,jdbcType=VARCHAR}, #{backupPhone1,jdbcType=VARCHAR}, ",
        "#{backupPhone2,jdbcType=VARCHAR}, #{emergencyContactName,jdbcType=VARCHAR}, ",
        "#{emergencyContactPhone,jdbcType=VARCHAR}, #{gender,jdbcType=VARCHAR}, ",
        "#{entryDate,jdbcType=DATE}, #{leaveDate,jdbcType=DATE}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP}, ",
        "#{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="employeeId", before=false, resultType=Long.class)
    int insert(Employee record);

    @InsertProvider(type=EmployeeSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="employeeId", before=false, resultType=Long.class)
    int insertSelective(Employee record);

    @SelectProvider(type=EmployeeSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="employee_id", property="employeeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dept_id", property="deptId", jdbcType=JdbcType.BIGINT),
        @Result(column="employee_name", property="employeeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_username", property="sysUsername", jdbcType=JdbcType.VARCHAR),
        @Result(column="employee_code", property="employeeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="identity_type", property="identityType", jdbcType=JdbcType.INTEGER),
        @Result(column="identity_no", property="identityNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="ident_pic", property="identPic", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="wechat", property="wechat", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_1", property="backupPhone1", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_2", property="backupPhone2", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_phone", property="emergencyContactPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
        @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.DATE),
        @Result(column="leave_date", property="leaveDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Employee> selectByExampleWithBLOBs(EmployeeExample example);

    @SelectProvider(type=EmployeeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="employee_id", property="employeeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dept_id", property="deptId", jdbcType=JdbcType.BIGINT),
        @Result(column="employee_name", property="employeeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_username", property="sysUsername", jdbcType=JdbcType.VARCHAR),
        @Result(column="employee_code", property="employeeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="identity_type", property="identityType", jdbcType=JdbcType.INTEGER),
        @Result(column="identity_no", property="identityNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="ident_pic", property="identPic", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="wechat", property="wechat", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_1", property="backupPhone1", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_2", property="backupPhone2", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_phone", property="emergencyContactPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
        @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.DATE),
        @Result(column="leave_date", property="leaveDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Employee> selectByExample(EmployeeExample example);

    @Select({
        "select",
        "employee_id, product_inst_id, dept_id, employee_name, phone, sys_username, employee_code, ",
        "position, status, alias_name, identity_type, identity_no, ident_pic, email, ",
        "wechat, backup_phone_1, backup_phone_2, emergency_contact_name, emergency_contact_phone, ",
        "gender, entry_date, leave_date, create_time, modify_time, remark",
        "from t_employee",
        "where employee_id = #{employeeId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="employee_id", property="employeeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dept_id", property="deptId", jdbcType=JdbcType.BIGINT),
        @Result(column="employee_name", property="employeeName", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="sys_username", property="sysUsername", jdbcType=JdbcType.VARCHAR),
        @Result(column="employee_code", property="employeeCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="position", property="position", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="identity_type", property="identityType", jdbcType=JdbcType.INTEGER),
        @Result(column="identity_no", property="identityNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="ident_pic", property="identPic", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="wechat", property="wechat", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_1", property="backupPhone1", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_2", property="backupPhone2", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_phone", property="emergencyContactPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
        @Result(column="entry_date", property="entryDate", jdbcType=JdbcType.DATE),
        @Result(column="leave_date", property="leaveDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Employee selectByPrimaryKey(Long employeeId);

    @UpdateProvider(type=EmployeeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    @UpdateProvider(type=EmployeeSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Employee record, @Param("example") EmployeeExample example);

    @UpdateProvider(type=EmployeeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    @UpdateProvider(type=EmployeeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Employee record);

    @Update({
        "update t_employee",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "dept_id = #{deptId,jdbcType=BIGINT},",
          "employee_name = #{employeeName,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "sys_username = #{sysUsername,jdbcType=VARCHAR},",
          "employee_code = #{employeeCode,jdbcType=VARCHAR},",
          "position = #{position,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "alias_name = #{aliasName,jdbcType=VARCHAR},",
          "identity_type = #{identityType,jdbcType=INTEGER},",
          "identity_no = #{identityNo,jdbcType=VARCHAR},",
          "ident_pic = #{identPic,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "wechat = #{wechat,jdbcType=VARCHAR},",
          "backup_phone_1 = #{backupPhone1,jdbcType=VARCHAR},",
          "backup_phone_2 = #{backupPhone2,jdbcType=VARCHAR},",
          "emergency_contact_name = #{emergencyContactName,jdbcType=VARCHAR},",
          "emergency_contact_phone = #{emergencyContactPhone,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=VARCHAR},",
          "entry_date = #{entryDate,jdbcType=DATE},",
          "leave_date = #{leaveDate,jdbcType=DATE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where employee_id = #{employeeId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Employee record);

    @Update({
        "update t_employee",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "dept_id = #{deptId,jdbcType=BIGINT},",
          "employee_name = #{employeeName,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "sys_username = #{sysUsername,jdbcType=VARCHAR},",
          "employee_code = #{employeeCode,jdbcType=VARCHAR},",
          "position = #{position,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "alias_name = #{aliasName,jdbcType=VARCHAR},",
          "identity_type = #{identityType,jdbcType=INTEGER},",
          "identity_no = #{identityNo,jdbcType=VARCHAR},",
          "ident_pic = #{identPic,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "wechat = #{wechat,jdbcType=VARCHAR},",
          "backup_phone_1 = #{backupPhone1,jdbcType=VARCHAR},",
          "backup_phone_2 = #{backupPhone2,jdbcType=VARCHAR},",
          "emergency_contact_name = #{emergencyContactName,jdbcType=VARCHAR},",
          "emergency_contact_phone = #{emergencyContactPhone,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=VARCHAR},",
          "entry_date = #{entryDate,jdbcType=DATE},",
          "leave_date = #{leaveDate,jdbcType=DATE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where employee_id = #{employeeId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Employee record);
}