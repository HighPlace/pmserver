package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.Bill;
import com.highplace.biz.pm.domain.charge.BillExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface BillMapper {

    // ----- mybatis generator外新增的方法------ //
    @SelectProvider(type=BillSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
            @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
            @Result(column="bill_name", property="billName", jdbcType=JdbcType.VARCHAR),
            @Result(column="bill_day", property="billDay", jdbcType=JdbcType.INTEGER),
            @Result(column="bill_cycle", property="billCycle", jdbcType=JdbcType.INTEGER),
            @Result(column="last_pay_day", property="lastPayDay", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="bill_id", property="billSubjectRelList", many=@Many(select="com.highplace.biz.pm.dao.charge.BillSubjectRelMapper.selectByBillIdWithSubjectName")),
    })
    List<Bill> selectByExampleWithBLOBsWithRelation(BillExample example);

    // ----- end ------ //

    @SelectProvider(type=BillSqlProvider.class, method="countByExample")
    long countByExample(BillExample example);

    @DeleteProvider(type=BillSqlProvider.class, method="deleteByExample")
    int deleteByExample(BillExample example);

    @Delete({
        "delete from t_charge_bill",
        "where bill_id = #{billId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long billId);

    @Insert({
        "insert into t_charge_bill (product_inst_id, bill_name, ",
        "bill_day, bill_cycle, ",
        "last_pay_day, create_time, ",
        "modify_time, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{billName,jdbcType=VARCHAR}, ",
        "#{billDay,jdbcType=INTEGER}, #{billCycle,jdbcType=INTEGER}, ",
        "#{lastPayDay,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="billId", before=false, resultType=Long.class)
    int insert(Bill record);

    @InsertProvider(type=BillSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="billId", before=false, resultType=Long.class)
    int insertSelective(Bill record);

    @SelectProvider(type=BillSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_name", property="billName", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_day", property="billDay", jdbcType=JdbcType.INTEGER),
        @Result(column="bill_cycle", property="billCycle", jdbcType=JdbcType.INTEGER),
        @Result(column="last_pay_day", property="lastPayDay", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Bill> selectByExampleWithBLOBs(BillExample example);

    @SelectProvider(type=BillSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_name", property="billName", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_day", property="billDay", jdbcType=JdbcType.INTEGER),
        @Result(column="bill_cycle", property="billCycle", jdbcType=JdbcType.INTEGER),
        @Result(column="last_pay_day", property="lastPayDay", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Bill> selectByExample(BillExample example);

    @Select({
        "select",
        "bill_id, product_inst_id, bill_name, bill_day, bill_cycle, last_pay_day, create_time, ",
        "modify_time, remark",
        "from t_charge_bill",
        "where bill_id = #{billId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_name", property="billName", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_day", property="billDay", jdbcType=JdbcType.INTEGER),
        @Result(column="bill_cycle", property="billCycle", jdbcType=JdbcType.INTEGER),
        @Result(column="last_pay_day", property="lastPayDay", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Bill selectByPrimaryKey(Long billId);

    @UpdateProvider(type=BillSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Bill record, @Param("example") BillExample example);

    @UpdateProvider(type=BillSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Bill record, @Param("example") BillExample example);

    @UpdateProvider(type=BillSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Bill record, @Param("example") BillExample example);

    @UpdateProvider(type=BillSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Bill record);

    @Update({
        "update t_charge_bill",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "bill_name = #{billName,jdbcType=VARCHAR},",
          "bill_day = #{billDay,jdbcType=INTEGER},",
          "bill_cycle = #{billCycle,jdbcType=INTEGER},",
          "last_pay_day = #{lastPayDay,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where bill_id = #{billId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Bill record);

    @Update({
        "update t_charge_bill",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "bill_name = #{billName,jdbcType=VARCHAR},",
          "bill_day = #{billDay,jdbcType=INTEGER},",
          "bill_cycle = #{billCycle,jdbcType=INTEGER},",
          "last_pay_day = #{lastPayDay,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where bill_id = #{billId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Bill record);
}