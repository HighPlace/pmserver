package com.highplace.biz.pm.dao;

import com.highplace.biz.pm.domain.Property;
import com.highplace.biz.pm.domain.PropertyExample;
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

public interface PropertyMapper {
    @SelectProvider(type=PropertySqlProvider.class, method="countByExample")
    long countByExample(PropertyExample example);

    @DeleteProvider(type=PropertySqlProvider.class, method="deleteByExample")
    int deleteByExample(PropertyExample example);

    @Delete({
        "delete from t_property",
        "where property_id = #{propertyId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long propertyId);

    @Insert({
        "insert into t_property (product_inst_id, property_type, ",
        "zone_id, building_id, ",
        "unit_id, room_id, ",
        "area_unit, property_area, ",
        "floor_area, house_type, ",
        "status, create_time, ",
        "modify_time, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{propertyType,jdbcType=INTEGER}, ",
        "#{zoneId,jdbcType=VARCHAR}, #{buildingId,jdbcType=VARCHAR}, ",
        "#{unitId,jdbcType=VARCHAR}, #{roomId,jdbcType=VARCHAR}, ",
        "#{areaUnit,jdbcType=INTEGER}, #{propertyArea,jdbcType=DOUBLE}, ",
        "#{floorArea,jdbcType=DOUBLE}, #{houseType,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="propertyId", before=false, resultType=Long.class)
    int insert(Property record);

    @InsertProvider(type=PropertySqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="propertyId", before=false, resultType=Long.class)
    int insertSelective(Property record);

    @SelectProvider(type=PropertySqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="property_type", property="propertyType", jdbcType=JdbcType.INTEGER),
        @Result(column="zone_id", property="zoneId", jdbcType=JdbcType.VARCHAR),
        @Result(column="building_id", property="buildingId", jdbcType=JdbcType.VARCHAR),
        @Result(column="unit_id", property="unitId", jdbcType=JdbcType.VARCHAR),
        @Result(column="room_id", property="roomId", jdbcType=JdbcType.VARCHAR),
        @Result(column="area_unit", property="areaUnit", jdbcType=JdbcType.INTEGER),
        @Result(column="property_area", property="propertyArea", jdbcType=JdbcType.DOUBLE),
        @Result(column="floor_area", property="floorArea", jdbcType=JdbcType.DOUBLE),
        @Result(column="house_type", property="houseType", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Property> selectByExampleWithBLOBs(PropertyExample example);

    @SelectProvider(type=PropertySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="property_type", property="propertyType", jdbcType=JdbcType.INTEGER),
        @Result(column="zone_id", property="zoneId", jdbcType=JdbcType.VARCHAR),
        @Result(column="building_id", property="buildingId", jdbcType=JdbcType.VARCHAR),
        @Result(column="unit_id", property="unitId", jdbcType=JdbcType.VARCHAR),
        @Result(column="room_id", property="roomId", jdbcType=JdbcType.VARCHAR),
        @Result(column="area_unit", property="areaUnit", jdbcType=JdbcType.INTEGER),
        @Result(column="property_area", property="propertyArea", jdbcType=JdbcType.DOUBLE),
        @Result(column="floor_area", property="floorArea", jdbcType=JdbcType.DOUBLE),
        @Result(column="house_type", property="houseType", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Property> selectByExample(PropertyExample example);

    @Select({
        "select",
        "property_id, product_inst_id, property_type, zone_id, building_id, unit_id, ",
        "room_id, area_unit, property_area, floor_area, house_type, status, create_time, ",
        "modify_time, remark",
        "from t_property",
        "where property_id = #{propertyId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="property_type", property="propertyType", jdbcType=JdbcType.INTEGER),
        @Result(column="zone_id", property="zoneId", jdbcType=JdbcType.VARCHAR),
        @Result(column="building_id", property="buildingId", jdbcType=JdbcType.VARCHAR),
        @Result(column="unit_id", property="unitId", jdbcType=JdbcType.VARCHAR),
        @Result(column="room_id", property="roomId", jdbcType=JdbcType.VARCHAR),
        @Result(column="area_unit", property="areaUnit", jdbcType=JdbcType.INTEGER),
        @Result(column="property_area", property="propertyArea", jdbcType=JdbcType.DOUBLE),
        @Result(column="floor_area", property="floorArea", jdbcType=JdbcType.DOUBLE),
        @Result(column="house_type", property="houseType", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Property selectByPrimaryKey(Long propertyId);

    @UpdateProvider(type=PropertySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Property record, @Param("example") PropertyExample example);

    @UpdateProvider(type=PropertySqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Property record, @Param("example") PropertyExample example);

    @UpdateProvider(type=PropertySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Property record, @Param("example") PropertyExample example);

    @UpdateProvider(type=PropertySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Property record);

    @Update({
        "update t_property",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "property_type = #{propertyType,jdbcType=INTEGER},",
          "zone_id = #{zoneId,jdbcType=VARCHAR},",
          "building_id = #{buildingId,jdbcType=VARCHAR},",
          "unit_id = #{unitId,jdbcType=VARCHAR},",
          "room_id = #{roomId,jdbcType=VARCHAR},",
          "area_unit = #{areaUnit,jdbcType=INTEGER},",
          "property_area = #{propertyArea,jdbcType=DOUBLE},",
          "floor_area = #{floorArea,jdbcType=DOUBLE},",
          "house_type = #{houseType,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where property_id = #{propertyId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Property record);

    @Update({
        "update t_property",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "property_type = #{propertyType,jdbcType=INTEGER},",
          "zone_id = #{zoneId,jdbcType=VARCHAR},",
          "building_id = #{buildingId,jdbcType=VARCHAR},",
          "unit_id = #{unitId,jdbcType=VARCHAR},",
          "room_id = #{roomId,jdbcType=VARCHAR},",
          "area_unit = #{areaUnit,jdbcType=INTEGER},",
          "property_area = #{propertyArea,jdbcType=DOUBLE},",
          "floor_area = #{floorArea,jdbcType=DOUBLE},",
          "house_type = #{houseType,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where property_id = #{propertyId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Property record);


}