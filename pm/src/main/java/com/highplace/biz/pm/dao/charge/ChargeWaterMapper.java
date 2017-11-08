package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.ChargeWater;
import com.highplace.biz.pm.domain.charge.ChargeWaterExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface ChargeWaterMapper {
    @SelectProvider(type=ChargeWaterSqlProvider.class, method="countByExample")
    long countByExample(ChargeWaterExample example);

    @DeleteProvider(type=ChargeWaterSqlProvider.class, method="deleteByExample")
    int deleteByExample(ChargeWaterExample example);

    @Delete({
        "delete from t_charge_water",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and charge_id = #{chargeId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}",
          "and fee_data_type = #{feeDataType,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(@Param("productInstId") String productInstId, @Param("chargeId") Long chargeId, @Param("propertyId") Long propertyId, @Param("feeDataType") Integer feeDataType);

    @Insert({
        "insert into t_charge_water (product_inst_id, charge_id, ",
        "property_id, fee_data_type, ",
        "property_name, begin_date, ",
        "end_date, begin_usage, ",
        "end_usage, create_time, ",
        "modify_time, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{chargeId,jdbcType=BIGINT}, ",
        "#{propertyId,jdbcType=BIGINT}, #{feeDataType,jdbcType=INTEGER}, ",
        "#{propertyName,jdbcType=VARCHAR}, #{beginDate,jdbcType=DATE}, ",
        "#{endDate,jdbcType=DATE}, #{beginUsage,jdbcType=DOUBLE}, ",
        "#{endUsage,jdbcType=DOUBLE}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=LONGVARCHAR})"
    })
    int insert(ChargeWater record);

    @InsertProvider(type=ChargeWaterSqlProvider.class, method="insertSelective")
    int insertSelective(ChargeWater record);

    @SelectProvider(type=ChargeWaterSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="fee_data_type", property="feeDataType", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="property_name", property="propertyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="begin_date", property="beginDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="begin_usage", property="beginUsage", jdbcType=JdbcType.DOUBLE),
        @Result(column="end_usage", property="endUsage", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<ChargeWater> selectByExampleWithBLOBs(ChargeWaterExample example);

    @SelectProvider(type=ChargeWaterSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="fee_data_type", property="feeDataType", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="property_name", property="propertyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="begin_date", property="beginDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="begin_usage", property="beginUsage", jdbcType=JdbcType.DOUBLE),
        @Result(column="end_usage", property="endUsage", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ChargeWater> selectByExample(ChargeWaterExample example);

    @Select({
        "select",
        "product_inst_id, charge_id, property_id, fee_data_type, property_name, begin_date, ",
        "end_date, begin_usage, end_usage, create_time, modify_time, remark",
        "from t_charge_water",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and charge_id = #{chargeId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}",
          "and fee_data_type = #{feeDataType,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="charge_id", property="chargeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="fee_data_type", property="feeDataType", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="property_name", property="propertyName", jdbcType=JdbcType.VARCHAR),
        @Result(column="begin_date", property="beginDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="begin_usage", property="beginUsage", jdbcType=JdbcType.DOUBLE),
        @Result(column="end_usage", property="endUsage", jdbcType=JdbcType.DOUBLE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    ChargeWater selectByPrimaryKey(@Param("productInstId") String productInstId, @Param("chargeId") Long chargeId, @Param("propertyId") Long propertyId, @Param("feeDataType") Integer feeDataType);

    @UpdateProvider(type=ChargeWaterSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ChargeWater record, @Param("example") ChargeWaterExample example);

    @UpdateProvider(type=ChargeWaterSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") ChargeWater record, @Param("example") ChargeWaterExample example);

    @UpdateProvider(type=ChargeWaterSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ChargeWater record, @Param("example") ChargeWaterExample example);

    @UpdateProvider(type=ChargeWaterSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ChargeWater record);

    @Update({
        "update t_charge_water",
        "set property_name = #{propertyName,jdbcType=VARCHAR},",
          "begin_date = #{beginDate,jdbcType=DATE},",
          "end_date = #{endDate,jdbcType=DATE},",
          "begin_usage = #{beginUsage,jdbcType=DOUBLE},",
          "end_usage = #{endUsage,jdbcType=DOUBLE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and charge_id = #{chargeId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}",
          "and fee_data_type = #{feeDataType,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(ChargeWater record);

    @Update({
        "update t_charge_water",
        "set property_name = #{propertyName,jdbcType=VARCHAR},",
          "begin_date = #{beginDate,jdbcType=DATE},",
          "end_date = #{endDate,jdbcType=DATE},",
          "begin_usage = #{beginUsage,jdbcType=DOUBLE},",
          "end_usage = #{endUsage,jdbcType=DOUBLE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and charge_id = #{chargeId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}",
          "and fee_data_type = #{feeDataType,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ChargeWater record);
}