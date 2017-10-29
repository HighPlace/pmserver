package com.highplace.biz.pm.dao.service;

import com.highplace.biz.pm.domain.service.Request;
import com.highplace.biz.pm.domain.service.RequestExample;
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

public interface RequestMapper {
    @SelectProvider(type=RequestSqlProvider.class, method="countByExample")
    long countByExample(RequestExample example);

    @DeleteProvider(type=RequestSqlProvider.class, method="deleteByExample")
    int deleteByExample(RequestExample example);

    @Delete({
        "delete from t_request",
        "where request_id = #{requestId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long requestId);

    @Insert({
        "insert into t_request (product_inst_id, type, ",
        "sub_type, source, ",
        "status, attachment1, ",
        "attachment2, property_id, ",
        "submitter, contact_info, ",
        "priority, start_time, ",
        "assign_time, accept_time, ",
        "finish_time, rate_time, ",
        "deal_desc, rate_level, ",
        "rate_num, rate_attachment, ",
        "rate_username, create_time, ",
        "modify_time, content, ",
        "rate_desc, remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, ",
        "#{subType,jdbcType=VARCHAR}, #{source,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER}, #{attachment1,jdbcType=VARCHAR}, ",
        "#{attachment2,jdbcType=VARCHAR}, #{propertyId,jdbcType=VARCHAR}, ",
        "#{submitter,jdbcType=VARCHAR}, #{contactInfo,jdbcType=VARCHAR}, ",
        "#{priority,jdbcType=INTEGER}, #{startTime,jdbcType=TIMESTAMP}, ",
        "#{assignTime,jdbcType=TIMESTAMP}, #{acceptTime,jdbcType=TIMESTAMP}, ",
        "#{finishTime,jdbcType=TIMESTAMP}, #{rateTime,jdbcType=TIMESTAMP}, ",
        "#{dealDesc,jdbcType=VARCHAR}, #{rateLevel,jdbcType=INTEGER}, ",
        "#{rateNum,jdbcType=INTEGER}, #{rateAttachment,jdbcType=VARCHAR}, ",
        "#{rateUsername,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{content,jdbcType=LONGVARCHAR}, ",
        "#{rateDesc,jdbcType=LONGVARCHAR}, #{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="requestId", before=false, resultType=Long.class)
    int insert(Request record);

    @InsertProvider(type=RequestSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="requestId", before=false, resultType=Long.class)
    int insertSelective(Request record);

    @SelectProvider(type=RequestSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="request_id", property="requestId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_type", property="subType", jdbcType=JdbcType.VARCHAR),
        @Result(column="source", property="source", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="attachment1", property="attachment1", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment2", property="attachment2", jdbcType=JdbcType.VARCHAR),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="submitter", property="submitter", jdbcType=JdbcType.VARCHAR),
        @Result(column="contact_info", property="contactInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="assign_time", property="assignTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="accept_time", property="acceptTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="finish_time", property="finishTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="rate_time", property="rateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="deal_desc", property="dealDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="rate_level", property="rateLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="rate_num", property="rateNum", jdbcType=JdbcType.INTEGER),
        @Result(column="rate_attachment", property="rateAttachment", jdbcType=JdbcType.VARCHAR),
        @Result(column="rate_username", property="rateUsername", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="rate_desc", property="rateDesc", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Request> selectByExampleWithBLOBs(RequestExample example);

    @SelectProvider(type=RequestSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="request_id", property="requestId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_type", property="subType", jdbcType=JdbcType.VARCHAR),
        @Result(column="source", property="source", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="attachment1", property="attachment1", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment2", property="attachment2", jdbcType=JdbcType.VARCHAR),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="submitter", property="submitter", jdbcType=JdbcType.VARCHAR),
        @Result(column="contact_info", property="contactInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="assign_time", property="assignTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="accept_time", property="acceptTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="finish_time", property="finishTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="rate_time", property="rateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="deal_desc", property="dealDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="rate_level", property="rateLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="rate_num", property="rateNum", jdbcType=JdbcType.INTEGER),
        @Result(column="rate_attachment", property="rateAttachment", jdbcType=JdbcType.VARCHAR),
        @Result(column="rate_username", property="rateUsername", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Request> selectByExample(RequestExample example);

    @Select({
        "select",
        "request_id, product_inst_id, type, sub_type, source, status, attachment1, attachment2, ",
        "property_id, submitter, contact_info, priority, start_time, assign_time, accept_time, ",
        "finish_time, rate_time, deal_desc, rate_level, rate_num, rate_attachment, rate_username, ",
        "create_time, modify_time, content, rate_desc, remark",
        "from t_request",
        "where request_id = #{requestId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="request_id", property="requestId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="sub_type", property="subType", jdbcType=JdbcType.VARCHAR),
        @Result(column="source", property="source", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="attachment1", property="attachment1", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment2", property="attachment2", jdbcType=JdbcType.VARCHAR),
        @Result(column="property_id", property="propertyId", jdbcType=JdbcType.VARCHAR),
        @Result(column="submitter", property="submitter", jdbcType=JdbcType.VARCHAR),
        @Result(column="contact_info", property="contactInfo", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="assign_time", property="assignTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="accept_time", property="acceptTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="finish_time", property="finishTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="rate_time", property="rateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="deal_desc", property="dealDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="rate_level", property="rateLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="rate_num", property="rateNum", jdbcType=JdbcType.INTEGER),
        @Result(column="rate_attachment", property="rateAttachment", jdbcType=JdbcType.VARCHAR),
        @Result(column="rate_username", property="rateUsername", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="rate_desc", property="rateDesc", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Request selectByPrimaryKey(Long requestId);

    @UpdateProvider(type=RequestSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Request record, @Param("example") RequestExample example);

    @UpdateProvider(type=RequestSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Request record, @Param("example") RequestExample example);

    @UpdateProvider(type=RequestSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Request record, @Param("example") RequestExample example);

    @UpdateProvider(type=RequestSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Request record);

    @Update({
        "update t_request",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "sub_type = #{subType,jdbcType=VARCHAR},",
          "source = #{source,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "attachment1 = #{attachment1,jdbcType=VARCHAR},",
          "attachment2 = #{attachment2,jdbcType=VARCHAR},",
          "property_id = #{propertyId,jdbcType=VARCHAR},",
          "submitter = #{submitter,jdbcType=VARCHAR},",
          "contact_info = #{contactInfo,jdbcType=VARCHAR},",
          "priority = #{priority,jdbcType=INTEGER},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "assign_time = #{assignTime,jdbcType=TIMESTAMP},",
          "accept_time = #{acceptTime,jdbcType=TIMESTAMP},",
          "finish_time = #{finishTime,jdbcType=TIMESTAMP},",
          "rate_time = #{rateTime,jdbcType=TIMESTAMP},",
          "deal_desc = #{dealDesc,jdbcType=VARCHAR},",
          "rate_level = #{rateLevel,jdbcType=INTEGER},",
          "rate_num = #{rateNum,jdbcType=INTEGER},",
          "rate_attachment = #{rateAttachment,jdbcType=VARCHAR},",
          "rate_username = #{rateUsername,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR},",
          "rate_desc = #{rateDesc,jdbcType=LONGVARCHAR},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where request_id = #{requestId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Request record);

    @Update({
        "update t_request",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "sub_type = #{subType,jdbcType=VARCHAR},",
          "source = #{source,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "attachment1 = #{attachment1,jdbcType=VARCHAR},",
          "attachment2 = #{attachment2,jdbcType=VARCHAR},",
          "property_id = #{propertyId,jdbcType=VARCHAR},",
          "submitter = #{submitter,jdbcType=VARCHAR},",
          "contact_info = #{contactInfo,jdbcType=VARCHAR},",
          "priority = #{priority,jdbcType=INTEGER},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "assign_time = #{assignTime,jdbcType=TIMESTAMP},",
          "accept_time = #{acceptTime,jdbcType=TIMESTAMP},",
          "finish_time = #{finishTime,jdbcType=TIMESTAMP},",
          "rate_time = #{rateTime,jdbcType=TIMESTAMP},",
          "deal_desc = #{dealDesc,jdbcType=VARCHAR},",
          "rate_level = #{rateLevel,jdbcType=INTEGER},",
          "rate_num = #{rateNum,jdbcType=INTEGER},",
          "rate_attachment = #{rateAttachment,jdbcType=VARCHAR},",
          "rate_username = #{rateUsername,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where request_id = #{requestId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Request record);
}