package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.Subject;
import com.highplace.biz.pm.domain.charge.SubjectExample;
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

public interface SubjectMapper {
    @SelectProvider(type=SubjectSqlProvider.class, method="countByExample")
    long countByExample(SubjectExample example);

    @DeleteProvider(type=SubjectSqlProvider.class, method="deleteByExample")
    int deleteByExample(SubjectExample example);

    @Delete({
        "delete from t_charge_subject",
        "where subject_id = #{subjectId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long subjectId);

    @Insert({
        "insert into t_charge_subject (product_inst_id, subject_name, ",
        "charge_cycle, cycle_flag, ",
        "late_fee, rate, fee_level_one, ",
        "level_one_toplimit, fee_level_two, ",
        "level_two_toplimit, fee_level_three, ",
        "level_three_toplimit, fee_data_unit, ",
        "fee_data_type, create_time, ",
        "modify_time, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{subjectName,jdbcType=VARCHAR}, ",
        "#{chargeCycle,jdbcType=INTEGER}, #{cycleFlag,jdbcType=INTEGER}, ",
        "#{lateFee,jdbcType=DOUBLE}, #{rate,jdbcType=DOUBLE}, #{feeLevelOne,jdbcType=DOUBLE}, ",
        "#{levelOneToplimit,jdbcType=DOUBLE}, #{feeLevelTwo,jdbcType=DOUBLE}, ",
        "#{levelTwoToplimit,jdbcType=DOUBLE}, #{feeLevelThree,jdbcType=DOUBLE}, ",
        "#{levelThreeToplimit,jdbcType=DOUBLE}, #{feeDataUnit,jdbcType=VARCHAR}, ",
        "#{feeDataType,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="subjectId", before=false, resultType=Long.class)
    int insert(Subject record);

    @InsertProvider(type=SubjectSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="subjectId", before=false, resultType=Long.class)
    int insertSelective(Subject record);

    @SelectProvider(type=SubjectSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="subject_name", property="subjectName", jdbcType=JdbcType.VARCHAR),
        @Result(column="charge_cycle", property="chargeCycle", jdbcType=JdbcType.INTEGER),
        @Result(column="cycle_flag", property="cycleFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="late_fee", property="lateFee", jdbcType=JdbcType.DOUBLE),
        @Result(column="rate", property="rate", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_one", property="feeLevelOne", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_one_toplimit", property="levelOneToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_two", property="feeLevelTwo", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_two_toplimit", property="levelTwoToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_three", property="feeLevelThree", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_three_toplimit", property="levelThreeToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_data_unit", property="feeDataUnit", jdbcType=JdbcType.VARCHAR),
        @Result(column="fee_data_type", property="feeDataType", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Subject> selectByExampleWithBLOBs(SubjectExample example);

    @SelectProvider(type=SubjectSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="subject_name", property="subjectName", jdbcType=JdbcType.VARCHAR),
        @Result(column="charge_cycle", property="chargeCycle", jdbcType=JdbcType.INTEGER),
        @Result(column="cycle_flag", property="cycleFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="late_fee", property="lateFee", jdbcType=JdbcType.DOUBLE),
        @Result(column="rate", property="rate", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_one", property="feeLevelOne", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_one_toplimit", property="levelOneToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_two", property="feeLevelTwo", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_two_toplimit", property="levelTwoToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_three", property="feeLevelThree", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_three_toplimit", property="levelThreeToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_data_unit", property="feeDataUnit", jdbcType=JdbcType.VARCHAR),
        @Result(column="fee_data_type", property="feeDataType", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Subject> selectByExample(SubjectExample example);

    @Select({
        "select",
        "subject_id, product_inst_id, subject_name, charge_cycle, cycle_flag, late_fee, ",
        "rate, fee_level_one, level_one_toplimit, fee_level_two, level_two_toplimit, ",
        "fee_level_three, level_three_toplimit, fee_data_unit, fee_data_type, create_time, ",
        "modify_time, remark",
        "from t_charge_subject",
        "where subject_id = #{subjectId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="subject_name", property="subjectName", jdbcType=JdbcType.VARCHAR),
        @Result(column="charge_cycle", property="chargeCycle", jdbcType=JdbcType.INTEGER),
        @Result(column="cycle_flag", property="cycleFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="late_fee", property="lateFee", jdbcType=JdbcType.DOUBLE),
        @Result(column="rate", property="rate", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_one", property="feeLevelOne", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_one_toplimit", property="levelOneToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_two", property="feeLevelTwo", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_two_toplimit", property="levelTwoToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_level_three", property="feeLevelThree", jdbcType=JdbcType.DOUBLE),
        @Result(column="level_three_toplimit", property="levelThreeToplimit", jdbcType=JdbcType.DOUBLE),
        @Result(column="fee_data_unit", property="feeDataUnit", jdbcType=JdbcType.VARCHAR),
        @Result(column="fee_data_type", property="feeDataType", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Subject selectByPrimaryKey(Long subjectId);

    @UpdateProvider(type=SubjectSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Subject record, @Param("example") SubjectExample example);

    @UpdateProvider(type=SubjectSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Subject record, @Param("example") SubjectExample example);

    @UpdateProvider(type=SubjectSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Subject record, @Param("example") SubjectExample example);

    @UpdateProvider(type=SubjectSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Subject record);

    @Update({
        "update t_charge_subject",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "subject_name = #{subjectName,jdbcType=VARCHAR},",
          "charge_cycle = #{chargeCycle,jdbcType=INTEGER},",
          "cycle_flag = #{cycleFlag,jdbcType=INTEGER},",
          "late_fee = #{lateFee,jdbcType=DOUBLE},",
          "rate = #{rate,jdbcType=DOUBLE},",
          "fee_level_one = #{feeLevelOne,jdbcType=DOUBLE},",
          "level_one_toplimit = #{levelOneToplimit,jdbcType=DOUBLE},",
          "fee_level_two = #{feeLevelTwo,jdbcType=DOUBLE},",
          "level_two_toplimit = #{levelTwoToplimit,jdbcType=DOUBLE},",
          "fee_level_three = #{feeLevelThree,jdbcType=DOUBLE},",
          "level_three_toplimit = #{levelThreeToplimit,jdbcType=DOUBLE},",
          "fee_data_unit = #{feeDataUnit,jdbcType=VARCHAR},",
          "fee_data_type = #{feeDataType,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where subject_id = #{subjectId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Subject record);

    @Update({
        "update t_charge_subject",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "subject_name = #{subjectName,jdbcType=VARCHAR},",
          "charge_cycle = #{chargeCycle,jdbcType=INTEGER},",
          "cycle_flag = #{cycleFlag,jdbcType=INTEGER},",
          "late_fee = #{lateFee,jdbcType=DOUBLE},",
          "rate = #{rate,jdbcType=DOUBLE},",
          "fee_level_one = #{feeLevelOne,jdbcType=DOUBLE},",
          "level_one_toplimit = #{levelOneToplimit,jdbcType=DOUBLE},",
          "fee_level_two = #{feeLevelTwo,jdbcType=DOUBLE},",
          "level_two_toplimit = #{levelTwoToplimit,jdbcType=DOUBLE},",
          "fee_level_three = #{feeLevelThree,jdbcType=DOUBLE},",
          "level_three_toplimit = #{levelThreeToplimit,jdbcType=DOUBLE},",
          "fee_data_unit = #{feeDataUnit,jdbcType=VARCHAR},",
          "fee_data_type = #{feeDataType,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where subject_id = #{subjectId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Subject record);
}