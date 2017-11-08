package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.ChargeDetail;
import com.highplace.biz.pm.domain.charge.ChargeDetailExample;
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

public interface ChargeDetailMapper {
    @SelectProvider(type=ChargeDetailSqlProvider.class, method="countByExample")
    long countByExample(ChargeDetailExample example);

    @DeleteProvider(type=ChargeDetailSqlProvider.class, method="deleteByExample")
    int deleteByExample(ChargeDetailExample example);

    @Delete({
        "delete from t_charge_detail",
        "where detail_id = #{detailId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long detailId);

    @Insert({
        "insert into t_charge_detail (product_inst_id, charge_id, ",
        "property_id, amount, ",
        "pay_status, pay_type, ",
        "pay_id, create_time, ",
        "modify_time, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{chargeId,jdbcType=BIGINT}, ",
        "#{propertyId,jdbcType=BIGINT}, #{amount,jdbcType=DOUBLE}, ",
        "#{payStatus,jdbcType=INTEGER}, #{payType,jdbcType=INTEGER}, ",
        "#{payId,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="detailId", before=false, resultType=Long.class)
    int insert(ChargeDetail record);

    @InsertProvider(type=ChargeDetailSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="detailId", before=false, resultType=Long.class)
    int insertSelective(ChargeDetail record);

    @SelectProvider(type=ChargeDetailSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="detail_id", property="detailId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DOUBLE),
        @Result(column="pay_status", property="payStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="pay_type", property="payType", jdbcType=JdbcType.INTEGER),
        @Result(column="pay_id", property="payId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<ChargeDetail> selectByExampleWithBLOBs(ChargeDetailExample example);

    @SelectProvider(type=ChargeDetailSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="detail_id", property="detailId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DOUBLE),
        @Result(column="pay_status", property="payStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="pay_type", property="payType", jdbcType=JdbcType.INTEGER),
        @Result(column="pay_id", property="payId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ChargeDetail> selectByExample(ChargeDetailExample example);

    @Select({
        "select",
        "detail_id, product_inst_id, charge_id, property_id, amount, pay_status, pay_type, ",
        "pay_id, create_time, modify_time, remark",
        "from t_charge_detail",
        "where detail_id = #{detailId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="detail_id", property="detailId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DOUBLE),
        @Result(column="pay_status", property="payStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="pay_type", property="payType", jdbcType=JdbcType.INTEGER),
        @Result(column="pay_id", property="payId", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    ChargeDetail selectByPrimaryKey(Long detailId);

    @UpdateProvider(type=ChargeDetailSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ChargeDetail record, @Param("example") ChargeDetailExample example);

    @UpdateProvider(type=ChargeDetailSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") ChargeDetail record, @Param("example") ChargeDetailExample example);

    @UpdateProvider(type=ChargeDetailSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ChargeDetail record, @Param("example") ChargeDetailExample example);

    @UpdateProvider(type=ChargeDetailSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ChargeDetail record);

    @Update({
        "update t_charge_detail",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "charge_id = #{chargeId,jdbcType=BIGINT},",
          "property_id = #{propertyId,jdbcType=BIGINT},",
          "amount = #{amount,jdbcType=DOUBLE},",
          "pay_status = #{payStatus,jdbcType=INTEGER},",
          "pay_type = #{payType,jdbcType=INTEGER},",
          "pay_id = #{payId,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where detail_id = #{detailId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(ChargeDetail record);

    @Update({
        "update t_charge_detail",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "charge_id = #{chargeId,jdbcType=BIGINT},",
          "property_id = #{propertyId,jdbcType=BIGINT},",
          "amount = #{amount,jdbcType=DOUBLE},",
          "pay_status = #{payStatus,jdbcType=INTEGER},",
          "pay_type = #{payType,jdbcType=INTEGER},",
          "pay_id = #{payId,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where detail_id = #{detailId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ChargeDetail record);
}