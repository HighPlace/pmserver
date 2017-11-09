package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.Charge;
import com.highplace.biz.pm.domain.charge.ChargeExample;
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

public interface ChargeMapper {
    @SelectProvider(type=ChargeSqlProvider.class, method="countByExample")
    long countByExample(ChargeExample example);

    @DeleteProvider(type=ChargeSqlProvider.class, method="deleteByExample")
    int deleteByExample(ChargeExample example);

    @Delete({
        "delete from t_charge",
        "where charge_id = #{chargeId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long chargeId);

    @Insert({
        "insert into t_charge (product_inst_id, bill_id, ",
        "bill_name, bill_period, ",
        "bill_date, status, ",
        "total_amount, received_amount, ",
        "create_time, modify_time, ",
        "remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{billId,jdbcType=BIGINT}, ",
        "#{billName,jdbcType=VARCHAR}, #{billPeriod,jdbcType=VARCHAR}, ",
        "#{billDate,jdbcType=TIMESTAMP}, #{status,jdbcType=INTEGER}, ",
        "#{totalAmount,jdbcType=DOUBLE}, #{receivedAmount,jdbcType=DOUBLE}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP}, ",
        "#{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="chargeId", before=false, resultType=Long.class)
    int insert(Charge record);

    @InsertProvider(type=ChargeSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="chargeId", before=false, resultType=Long.class)
    int insertSelective(Charge record);

    @SelectProvider(type=ChargeSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT),
        @Result(column="bill_name", property="billName", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_period", property="billPeriod", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_date", property="billDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="total_amount", property="totalAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="received_amount", property="receivedAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Charge> selectByExampleWithBLOBs(ChargeExample example);

    @SelectProvider(type=ChargeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT),
        @Result(column="bill_name", property="billName", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_period", property="billPeriod", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_date", property="billDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="total_amount", property="totalAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="received_amount", property="receivedAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Charge> selectByExample(ChargeExample example);

    @Select({
        "select",
        "charge_id, product_inst_id, bill_id, bill_name, bill_period, bill_date, status, ",
        "total_amount, received_amount, create_time, modify_time, remark",
        "from t_charge",
        "where charge_id = #{chargeId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_id", property="billId", jdbcType=JdbcType.BIGINT),
        @Result(column="bill_name", property="billName", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_period", property="billPeriod", jdbcType=JdbcType.VARCHAR),
        @Result(column="bill_date", property="billDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="total_amount", property="totalAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="received_amount", property="receivedAmount", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Charge selectByPrimaryKey(Long chargeId);

    @UpdateProvider(type=ChargeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Charge record, @Param("example") ChargeExample example);

    @UpdateProvider(type=ChargeSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Charge record, @Param("example") ChargeExample example);

    @UpdateProvider(type=ChargeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Charge record, @Param("example") ChargeExample example);

    @UpdateProvider(type=ChargeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Charge record);

    @Update({
        "update t_charge",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "bill_id = #{billId,jdbcType=BIGINT},",
          "bill_name = #{billName,jdbcType=VARCHAR},",
          "bill_period = #{billPeriod,jdbcType=VARCHAR},",
          "bill_date = #{billDate,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=INTEGER},",
          "total_amount = #{totalAmount,jdbcType=DOUBLE},",
          "received_amount = #{receivedAmount,jdbcType=DOUBLE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where charge_id = #{chargeId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Charge record);

    @Update({
        "update t_charge",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "bill_id = #{billId,jdbcType=BIGINT},",
          "bill_name = #{billName,jdbcType=VARCHAR},",
          "bill_period = #{billPeriod,jdbcType=VARCHAR},",
          "bill_date = #{billDate,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=INTEGER},",
          "total_amount = #{totalAmount,jdbcType=DOUBLE},",
          "received_amount = #{receivedAmount,jdbcType=DOUBLE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where charge_id = #{chargeId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Charge record);
}