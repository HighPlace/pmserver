package com.highplace.biz.pm.dao.base;

import com.highplace.biz.pm.domain.base.Relation;
import com.highplace.biz.pm.domain.base.RelationExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface RelationMapper {

    // ----- mybatis generator外新增的方法------ //
    @Select({
            "select",
            "a.relation_id, a.product_inst_id, a.customer_id, a.property_id, a.type, a.status, a.start_date, ",
            "a.end_date, a.create_time, a.modify_time, a.remark, ",
            "concat(b.zone_id, b.building_id, b.unit_id, b.room_id) as property_name",
            "from t_relation a, t_property b",
            "where a.customer_id = #{customerId,jdbcType=BIGINT}",
            "and a.property_id = b.property_id"
    })
    @Results({
            @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
            @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT),
            @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
            @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="property_name", property="propertyName", jdbcType=JdbcType.VARCHAR),
            @Result(column="relation_id", property="carList", many=@Many(select="com.highplace.biz.pm.dao.base.CarMapper.selectByRelationId")),
    })
    List<Relation> selectByCustomerIdWithCar(Long customerId);

    // ----- end ------ //

    @SelectProvider(type=RelationSqlProvider.class, method="countByExample")
    long countByExample(RelationExample example);

    @DeleteProvider(type=RelationSqlProvider.class, method="deleteByExample")
    int deleteByExample(RelationExample example);

    @Delete({
        "delete from t_relation",
        "where relation_id = #{relationId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long relationId);

    @Insert({
        "insert into t_relation (product_inst_id, customer_id, ",
        "property_id, type, ",
        "status, start_date, ",
        "end_date, create_time, ",
        "modify_time, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{customerId,jdbcType=BIGINT}, ",
        "#{propertyId,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER}, #{startDate,jdbcType=DATE}, ",
        "#{endDate,jdbcType=DATE}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="relationId", before=false, resultType=Long.class)
    int insert(Relation record);

    @InsertProvider(type=RelationSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="relationId", before=false, resultType=Long.class)
    int insertSelective(Relation record);

    @SelectProvider(type=RelationSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Relation> selectByExampleWithBLOBs(RelationExample example);

    @SelectProvider(type=RelationSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Relation> selectByExample(RelationExample example);

    @Select({
        "select",
        "relation_id, product_inst_id, customer_id, property_id, type, status, start_date, ",
        "end_date, create_time, modify_time, remark",
        "from t_relation",
        "where relation_id = #{relationId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="relation_id", property="relationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Relation selectByPrimaryKey(Long relationId);

    @UpdateProvider(type=RelationSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Relation record, @Param("example") RelationExample example);

    @UpdateProvider(type=RelationSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Relation record, @Param("example") RelationExample example);

    @UpdateProvider(type=RelationSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Relation record, @Param("example") RelationExample example);

    @UpdateProvider(type=RelationSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Relation record);

    @Update({
        "update t_relation",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "customer_id = #{customerId,jdbcType=BIGINT},",
          "property_id = #{propertyId,jdbcType=BIGINT},",
          "type = #{type,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "start_date = #{startDate,jdbcType=DATE},",
          "end_date = #{endDate,jdbcType=DATE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where relation_id = #{relationId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Relation record);

    @Update({
        "update t_relation",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "customer_id = #{customerId,jdbcType=BIGINT},",
          "property_id = #{propertyId,jdbcType=BIGINT},",
          "type = #{type,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "start_date = #{startDate,jdbcType=DATE},",
          "end_date = #{endDate,jdbcType=DATE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where relation_id = #{relationId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Relation record);
}