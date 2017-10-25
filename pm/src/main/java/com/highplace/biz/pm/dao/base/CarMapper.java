package com.highplace.biz.pm.dao.base;

import com.highplace.biz.pm.domain.base.Car;
import com.highplace.biz.pm.domain.base.CarExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface CarMapper {

    // ----- mybatis generator外新增的方法------ //
    @SelectProvider(type=CarSqlProvider.class, method="selectByExample")
    @Results({
            @Result(column="car_id", property="carId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
            @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT),
            @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
            @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="relation_id", property="relation", one=@One(select="com.highplace.biz.pm.dao.base.RelationMapper.selectByPrimaryKey"))
    })
    List<Car> selectByExampleWithRelation(CarExample example);

    @Select({
            "select",
            "car_id, product_inst_id, relation_id, plate_no, type, charge_status, park_info, ",
            "create_time, modify_time, remark",
            "from t_car",
            "where relation_id = #{relationId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="car_id", property="carId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
            @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT),
            @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
            @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Car> selectByRelationId(Long relationId);

    @Select("select product_inst_id, plate_no from t_car")
    @Results({
            @Result(column = "product_inst_id", property = "productInstId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "plate_no", property = "plateNo", jdbcType = JdbcType.VARCHAR)
    })
    List<Car> selectAllProductInstIdAndPlateNo();

    // ----- end ------ //

    @SelectProvider(type=CarSqlProvider.class, method="countByExample")
    long countByExample(CarExample example);

    @DeleteProvider(type=CarSqlProvider.class, method="deleteByExample")
    int deleteByExample(CarExample example);

    @Delete({
        "delete from t_car",
        "where car_id = #{carId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long carId);

    @Insert({
        "insert into t_car (product_inst_id, relation_id, ",
        "plate_no, type, charge_status, ",
        "park_info, create_time, ",
        "modify_time, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{relationId,jdbcType=BIGINT}, ",
        "#{plateNo,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, #{chargeStatus,jdbcType=INTEGER}, ",
        "#{parkInfo,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="carId", before=false, resultType=Long.class)
    int insert(Car record);

    @InsertProvider(type=CarSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="carId", before=false, resultType=Long.class)
    int insertSelective(Car record);

    @SelectProvider(type=CarSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="car_id", property="carId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT),
        @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Car> selectByExampleWithBLOBs(CarExample example);

    @SelectProvider(type=CarSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="car_id", property="carId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT),
        @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Car> selectByExample(CarExample example);

    @Select({
        "select",
        "car_id, product_inst_id, relation_id, plate_no, type, charge_status, park_info, ",
        "create_time, modify_time, remark",
        "from t_car",
        "where car_id = #{carId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="car_id", property="carId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT),
        @Result(column="plate_no", property="plateNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="charge_status", property="chargeStatus", jdbcType=JdbcType.INTEGER),
        @Result(column="park_info", property="parkInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Car selectByPrimaryKey(Long carId);

    @UpdateProvider(type=CarSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Car record, @Param("example") CarExample example);

    @UpdateProvider(type=CarSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Car record, @Param("example") CarExample example);

    @UpdateProvider(type=CarSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Car record, @Param("example") CarExample example);

    @UpdateProvider(type=CarSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Car record);

    @Update({
        "update t_car",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "relation_id = #{relationId,jdbcType=BIGINT},",
          "plate_no = #{plateNo,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "charge_status = #{chargeStatus,jdbcType=INTEGER},",
          "park_info = #{parkInfo,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where car_id = #{carId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Car record);

    @Update({
        "update t_car",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "relation_id = #{relationId,jdbcType=BIGINT},",
          "plate_no = #{plateNo,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "charge_status = #{chargeStatus,jdbcType=INTEGER},",
          "park_info = #{parkInfo,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where car_id = #{carId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Car record);
}