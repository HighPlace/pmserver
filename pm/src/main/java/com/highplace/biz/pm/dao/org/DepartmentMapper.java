package com.highplace.biz.pm.dao.org;

import com.highplace.biz.pm.domain.org.Department;
import com.highplace.biz.pm.domain.org.DepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DepartmentMapper {
    @SelectProvider(type=DepartmentSqlProvider.class, method="countByExample")
    long countByExample(DepartmentExample example);

    @DeleteProvider(type=DepartmentSqlProvider.class, method="deleteByExample")
    int deleteByExample(DepartmentExample example);

    @Delete({
        "delete from t_department",
        "where dept_id = #{deptId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long deptId);

    @Insert({
        "insert into t_department (product_inst_id, dept_name, ",
        "level, status, superior_dept_id, ",
        "dept_code, alias_name, ",
        "create_time, modify_time, ",
        "dept_desc, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{deptName,jdbcType=VARCHAR}, ",
        "#{level,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, #{superiorDeptId,jdbcType=BIGINT}, ",
        "#{deptCode,jdbcType=VARCHAR}, #{aliasName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP}, ",
        "#{deptDesc,jdbcType=LONGVARCHAR}, #{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="deptId", before=false, resultType=Long.class)
    int insert(Department record);

    @InsertProvider(type=DepartmentSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="deptId", before=false, resultType=Long.class)
    int insertSelective(Department record);

    @SelectProvider(type=DepartmentSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="dept_id", property="deptId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dept_name", property="deptName", jdbcType=JdbcType.VARCHAR),
        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="superior_dept_id", property="superiorDeptId", jdbcType=JdbcType.BIGINT),
        @Result(column="dept_code", property="deptCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="dept_desc", property="deptDesc", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Department> selectByExampleWithBLOBs(DepartmentExample example);

    @SelectProvider(type=DepartmentSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="dept_id", property="deptId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dept_name", property="deptName", jdbcType=JdbcType.VARCHAR),
        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="superior_dept_id", property="superiorDeptId", jdbcType=JdbcType.BIGINT),
        @Result(column="dept_code", property="deptCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Department> selectByExample(DepartmentExample example);

    @Select({
        "select",
        "dept_id, product_inst_id, dept_name, level, status, superior_dept_id, dept_code, ",
        "alias_name, create_time, modify_time, dept_desc, remark",
        "from t_department",
        "where dept_id = #{deptId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="dept_id", property="deptId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="dept_name", property="deptName", jdbcType=JdbcType.VARCHAR),
        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="superior_dept_id", property="superiorDeptId", jdbcType=JdbcType.BIGINT),
        @Result(column="dept_code", property="deptCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="dept_desc", property="deptDesc", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Department selectByPrimaryKey(Long deptId);

    @UpdateProvider(type=DepartmentSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    @UpdateProvider(type=DepartmentSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Department record, @Param("example") DepartmentExample example);

    @UpdateProvider(type=DepartmentSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    @UpdateProvider(type=DepartmentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Department record);

    @Update({
        "update t_department",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "dept_name = #{deptName,jdbcType=VARCHAR},",
          "level = #{level,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "superior_dept_id = #{superiorDeptId,jdbcType=BIGINT},",
          "dept_code = #{deptCode,jdbcType=VARCHAR},",
          "alias_name = #{aliasName,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "dept_desc = #{deptDesc,jdbcType=LONGVARCHAR},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where dept_id = #{deptId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Department record);

    @Update({
        "update t_department",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "dept_name = #{deptName,jdbcType=VARCHAR},",
          "level = #{level,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "superior_dept_id = #{superiorDeptId,jdbcType=BIGINT},",
          "dept_code = #{deptCode,jdbcType=VARCHAR},",
          "alias_name = #{aliasName,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where dept_id = #{deptId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Department record);
}