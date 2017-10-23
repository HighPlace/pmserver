package com.highplace.biz.pm.dao;

import com.highplace.biz.pm.domain.CustomerPropertyRel;
import com.highplace.biz.pm.domain.CustomerPropertyRelExample;
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

public interface CustomerPropertyRelMapper {

    @Select({
            "select",
            "a.product_inst_id, a.customer_id, a.property_id, a.type, a.status, a.start_date, a.end_date, ",
            "a.create_time, a.modify_time, a.remark, ",
            "concat(b.zone_id, b.building_id, b.unit_id, b.room_id) as property_name",
            "from t_customer_property_relation a, t_property b",
            "where a.customer_id = #{customerId,jdbcType=BIGINT} ",
            "and a.property_id = b.property_id"
    })
    @Results({
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
            @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="property_name", property="propertyName", jdbcType=JdbcType.VARCHAR)
    })
    List<CustomerPropertyRel> selectByCustomerId(@Param("customerId") Long customerId);
    ////以上为人工添加

    ////以下为generator自动生成
    @SelectProvider(type=CustomerPropertyRelSqlProvider.class, method="countByExample")
    long countByExample(CustomerPropertyRelExample example);

    @DeleteProvider(type=CustomerPropertyRelSqlProvider.class, method="deleteByExample")
    int deleteByExample(CustomerPropertyRelExample example);

    @Delete({
        "delete from t_customer_property_relation",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and customer_id = #{customerId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(@Param("productInstId") String productInstId, @Param("customerId") Long customerId, @Param("propertyId") Long propertyId);

    @Insert({
        "insert into t_customer_property_relation (product_inst_id, customer_id, ",
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
    int insert(CustomerPropertyRel record);

    @InsertProvider(type=CustomerPropertyRelSqlProvider.class, method="insertSelective")
    int insertSelective(CustomerPropertyRel record);

    @SelectProvider(type=CustomerPropertyRelSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<CustomerPropertyRel> selectByExampleWithBLOBs(CustomerPropertyRelExample example);

    @SelectProvider(type=CustomerPropertyRelSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<CustomerPropertyRel> selectByExample(CustomerPropertyRelExample example);

    @Select({
        "select",
        "product_inst_id, customer_id, property_id, type, status, start_date, end_date, ",
        "create_time, modify_time, remark",
        "from t_customer_property_relation",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and customer_id = #{customerId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    CustomerPropertyRel selectByPrimaryKey(@Param("productInstId") String productInstId, @Param("customerId") Long customerId, @Param("propertyId") Long propertyId);

    @UpdateProvider(type=CustomerPropertyRelSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CustomerPropertyRel record, @Param("example") CustomerPropertyRelExample example);

    @UpdateProvider(type=CustomerPropertyRelSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") CustomerPropertyRel record, @Param("example") CustomerPropertyRelExample example);

    @UpdateProvider(type=CustomerPropertyRelSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CustomerPropertyRel record, @Param("example") CustomerPropertyRelExample example);

    @UpdateProvider(type=CustomerPropertyRelSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CustomerPropertyRel record);

    @Update({
        "update t_customer_property_relation",
        "set type = #{type,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "start_date = #{startDate,jdbcType=DATE},",
          "end_date = #{endDate,jdbcType=DATE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and customer_id = #{customerId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(CustomerPropertyRel record);

    @Update({
        "update t_customer_property_relation",
        "set type = #{type,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "start_date = #{startDate,jdbcType=DATE},",
          "end_date = #{endDate,jdbcType=DATE},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where product_inst_id = #{productInstId,jdbcType=VARCHAR}",
          "and customer_id = #{customerId,jdbcType=BIGINT}",
          "and property_id = #{propertyId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CustomerPropertyRel record);
}