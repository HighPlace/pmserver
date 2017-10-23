package com.highplace.biz.pm.dao;

import com.highplace.biz.pm.domain.CustomerCar;
import com.highplace.biz.pm.domain.CustomerCarExample;
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

public interface CustomerCarMapper {

    @Select({
            "select",
            "a.product_inst_id, a.customer_id, a.property_id, a.plate_no, a.type, a.charge_status, a.park_info, ",
            "a.start_date, a.end_date, a.create_time, a.modify_time, a.remark, ",
            "concat(b.zone_id, b.building_id, b.unit_id, b.room_id) as property_name",
            "from t_customer_car a, t_property b",
            "where a.customer_id = #{customerId,jdbcType=BIGINT} ",
            "and a.property_id = b.property_id"
    })
    @Results({
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
            @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
            @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="property_name", property="propertyName", jdbcType=JdbcType.VARCHAR)
    })
    List<CustomerCar> selectByCustomerId(@Param("customerId") Long customerId);
    ////以上为人工添加

    ////以下为generator自动生成

    @SelectProvider(type=CustomerCarSqlProvider.class, method="countByExample")
    long countByExample(CustomerCarExample example);

    @DeleteProvider(type=CustomerCarSqlProvider.class, method="deleteByExample")
    int deleteByExample(CustomerCarExample example);

    @Delete({
        "delete from t_customer_car",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and customer_id = #{customerId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}",
          "and plate_no = #{plateNo,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(@Param("productInstId") String productInstId, @Param("customerId") Long customerId, @Param("propertyId") Long propertyId, @Param("plateNo") String plateNo);

    @Insert({
        "insert into t_customer_car (product_inst_id, customer_id, ",
        "property_id, plate_no, ",
        "type, charge_status, ",
        "park_info, start_date, ",
        "end_date, create_time, ",
        "modify_time, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{customerId,jdbcType=BIGINT}, ",
        "#{propertyId,jdbcType=BIGINT}, #{plateNo,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{chargeStatus,jdbcType=INTEGER}, ",
        "#{parkInfo,jdbcType=VARCHAR}, #{startDate,jdbcType=DATE}, ",
        "#{endDate,jdbcType=DATE}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=LONGVARCHAR})"
    })
    int insert(CustomerCar record);

    @InsertProvider(type=CustomerCarSqlProvider.class, method="insertSelective")
    int insertSelective(CustomerCar record);

    @SelectProvider(type=CustomerCarSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<CustomerCar> selectByExampleWithBLOBs(CustomerCarExample example);

    @SelectProvider(type=CustomerCarSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<CustomerCar> selectByExample(CustomerCarExample example);

    @Select({
        "select",
        "product_inst_id, customer_id, property_id, plate_no, type, charge_status, park_info, ",
        "start_date, end_date, create_time, modify_time, remark",
        "from t_customer_car",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and customer_id = #{customerId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}",
          "and plate_no = #{plateNo,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    CustomerCar selectByPrimaryKey(@Param("productInstId") String productInstId, @Param("customerId") Long customerId, @Param("propertyId") Long propertyId, @Param("plateNo") String plateNo);

    @UpdateProvider(type=CustomerCarSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CustomerCar record, @Param("example") CustomerCarExample example);

    @UpdateProvider(type=CustomerCarSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") CustomerCar record, @Param("example") CustomerCarExample example);

    @UpdateProvider(type=CustomerCarSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CustomerCar record, @Param("example") CustomerCarExample example);

    @UpdateProvider(type=CustomerCarSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CustomerCar record);

    @Update({
        "update t_customer_car",
        "set type = #{type,jdbcType=INTEGER},",
          "charge_status = #{chargeStatus,jdbcType=INTEGER},",
          "park_info = #{parkInfo,jdbcType=VARCHAR},",
          "start_date = #{startDate,jdbcType=DATE},",
          "end_date = #{endDate,jdbcType=DATE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and customer_id = #{customerId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}",
          "and plate_no = #{plateNo,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKeyWithBLOBs(CustomerCar record);

    @Update({
        "update t_customer_car",
        "set type = #{type,jdbcType=INTEGER},",
          "charge_status = #{chargeStatus,jdbcType=INTEGER},",
          "park_info = #{parkInfo,jdbcType=VARCHAR},",
          "start_date = #{startDate,jdbcType=DATE},",
          "end_date = #{endDate,jdbcType=DATE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and customer_id = #{customerId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}",
          "and plate_no = #{plateNo,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(CustomerCar record);
}