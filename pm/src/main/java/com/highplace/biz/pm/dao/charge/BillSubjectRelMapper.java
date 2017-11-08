package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.BillSubjectRel;
import com.highplace.biz.pm.domain.charge.BillSubjectRelExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface BillSubjectRelMapper {

    // ----- mybatis generator外新增的方法------ //
    @Select({
            "select",
            "relation_id, product_inst_id, bill_id, subject_id, create_time, modify_time",
            "from t_charge_bill_subject",
            "where bill_id = #{billId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
            @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT),
            @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="subject_id", property="subjectName", one=@One(select="com.highplace.biz.pm.dao.charge.SubjectMapper.selectSubjectNameByPrimaryKey")),
    })
    List<BillSubjectRel> selectByBillIdWithSubjectName(Long billId);

    // ----- end ------ //

    @SelectProvider(type=BillSubjectRelSqlProvider.class, method="countByExample")
    long countByExample(BillSubjectRelExample example);

    @DeleteProvider(type=BillSubjectRelSqlProvider.class, method="deleteByExample")
    int deleteByExample(BillSubjectRelExample example);

    @Delete({
        "delete from t_charge_bill_subject",
        "where relation_id = #{relationId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long relationId);

    @Insert({
        "insert into t_charge_bill_subject (product_inst_id, bill_id, ",
        "subject_id, create_time, ",
        "modify_time)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{billId,jdbcType=BIGINT}, ",
        "#{subjectId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="relationId", before=false, resultType=Long.class)
    int insert(BillSubjectRel record);

    @InsertProvider(type=BillSubjectRelSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="relationId", before=false, resultType=Long.class)
    int insertSelective(BillSubjectRel record);

    @SelectProvider(type=BillSubjectRelSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT),
        @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<BillSubjectRel> selectByExample(BillSubjectRelExample example);

    @Select({
        "select",
        "relation_id, product_inst_id, bill_id, subject_id, create_time, modify_time",
        "from t_charge_bill_subject",
        "where relation_id = #{relationId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT),
        @Result(column="subject_id", property="subjectId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    BillSubjectRel selectByPrimaryKey(Long relationId);

    @UpdateProvider(type=BillSubjectRelSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") BillSubjectRel record, @Param("example") BillSubjectRelExample example);

    @UpdateProvider(type=BillSubjectRelSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") BillSubjectRel record, @Param("example") BillSubjectRelExample example);

    @UpdateProvider(type=BillSubjectRelSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BillSubjectRel record);

    @Update({
        "update t_charge_bill_subject",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "bill_id = #{billId,jdbcType=BIGINT},",
          "subject_id = #{subjectId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where relation_id = #{relationId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(BillSubjectRel record);
}