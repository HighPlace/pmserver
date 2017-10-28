package com.highplace.biz.pm.dao.notice;

import com.highplace.biz.pm.domain.service.Notice;
import com.highplace.biz.pm.domain.service.NoticeExample;
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

public interface NoticeMapper {
    @SelectProvider(type=NoticeSqlProvider.class, method="countByExample")
    long countByExample(NoticeExample example);

    @DeleteProvider(type=NoticeSqlProvider.class, method="deleteByExample")
    int deleteByExample(NoticeExample example);

    @Delete({
        "delete from t_notice",
        "where notice_id = #{noticeId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long noticeId);

    @Insert({
        "insert into t_notice (product_inst_id, title, ",
        "type, status, publish_date, ",
        "valid_date, publisher, ",
        "approver, attachment_name, ",
        "attachment_link, specify_zone_id_list, ",
        "specify_building_id_list, specify_unit_id_list, ",
        "specify_room_id_list, create_time, ",
        "modify_time, content, ",
        "remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, #{publishDate,jdbcType=DATE}, ",
        "#{validDate,jdbcType=DATE}, #{publisher,jdbcType=VARCHAR}, ",
        "#{approver,jdbcType=VARCHAR}, #{attachmentName,jdbcType=VARCHAR}, ",
        "#{attachmentLink,jdbcType=VARCHAR}, #{specifyZoneIdList,jdbcType=VARCHAR}, ",
        "#{specifyBuildingIdList,jdbcType=VARCHAR}, #{specifyUnitIdList,jdbcType=VARCHAR}, ",
        "#{specifyRoomIdList,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{modifyTime,jdbcType=TIMESTAMP}, #{content,jdbcType=LONGVARCHAR}, ",
        "#{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="noticeId", before=false, resultType=Long.class)
    int insert(Notice record);

    @InsertProvider(type=NoticeSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="noticeId", before=false, resultType=Long.class)
    int insertSelective(Notice record);

    @SelectProvider(type=NoticeSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="notice_id", property="noticeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="publish_date", property="publishDate", jdbcType=JdbcType.DATE),
        @Result(column="valid_date", property="validDate", jdbcType=JdbcType.DATE),
        @Result(column="publisher", property="publisher", jdbcType=JdbcType.VARCHAR),
        @Result(column="approver", property="approver", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment_name", property="attachmentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment_link", property="attachmentLink", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_zone_id_list", property="specifyZoneIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_building_id_list", property="specifyBuildingIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_unit_id_list", property="specifyUnitIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_room_id_list", property="specifyRoomIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Notice> selectByExampleWithBLOBs(NoticeExample example);

    @SelectProvider(type=NoticeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="notice_id", property="noticeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="publish_date", property="publishDate", jdbcType=JdbcType.DATE),
        @Result(column="valid_date", property="validDate", jdbcType=JdbcType.DATE),
        @Result(column="publisher", property="publisher", jdbcType=JdbcType.VARCHAR),
        @Result(column="approver", property="approver", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment_name", property="attachmentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment_link", property="attachmentLink", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_zone_id_list", property="specifyZoneIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_building_id_list", property="specifyBuildingIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_unit_id_list", property="specifyUnitIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_room_id_list", property="specifyRoomIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Notice> selectByExample(NoticeExample example);

    @Select({
        "select",
        "notice_id, product_inst_id, title, type, status, publish_date, valid_date, publisher, ",
        "approver, attachment_name, attachment_link, specify_zone_id_list, specify_building_id_list, ",
        "specify_unit_id_list, specify_room_id_list, create_time, modify_time, content, ",
        "remark",
        "from t_notice",
        "where notice_id = #{noticeId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="notice_id", property="noticeId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="publish_date", property="publishDate", jdbcType=JdbcType.DATE),
        @Result(column="valid_date", property="validDate", jdbcType=JdbcType.DATE),
        @Result(column="publisher", property="publisher", jdbcType=JdbcType.VARCHAR),
        @Result(column="approver", property="approver", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment_name", property="attachmentName", jdbcType=JdbcType.VARCHAR),
        @Result(column="attachment_link", property="attachmentLink", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_zone_id_list", property="specifyZoneIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_building_id_list", property="specifyBuildingIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_unit_id_list", property="specifyUnitIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="specify_room_id_list", property="specifyRoomIdList", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Notice selectByPrimaryKey(Long noticeId);

    @UpdateProvider(type=NoticeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Notice record, @Param("example") NoticeExample example);

    @UpdateProvider(type=NoticeSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Notice record, @Param("example") NoticeExample example);

    @UpdateProvider(type=NoticeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Notice record, @Param("example") NoticeExample example);

    @UpdateProvider(type=NoticeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Notice record);

    @Update({
        "update t_notice",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "publish_date = #{publishDate,jdbcType=DATE},",
          "valid_date = #{validDate,jdbcType=DATE},",
          "publisher = #{publisher,jdbcType=VARCHAR},",
          "approver = #{approver,jdbcType=VARCHAR},",
          "attachment_name = #{attachmentName,jdbcType=VARCHAR},",
          "attachment_link = #{attachmentLink,jdbcType=VARCHAR},",
          "specify_zone_id_list = #{specifyZoneIdList,jdbcType=VARCHAR},",
          "specify_building_id_list = #{specifyBuildingIdList,jdbcType=VARCHAR},",
          "specify_unit_id_list = #{specifyUnitIdList,jdbcType=VARCHAR},",
          "specify_room_id_list = #{specifyRoomIdList,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where notice_id = #{noticeId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Notice record);

    @Update({
        "update t_notice",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "publish_date = #{publishDate,jdbcType=DATE},",
          "valid_date = #{validDate,jdbcType=DATE},",
          "publisher = #{publisher,jdbcType=VARCHAR},",
          "approver = #{approver,jdbcType=VARCHAR},",
          "attachment_name = #{attachmentName,jdbcType=VARCHAR},",
          "attachment_link = #{attachmentLink,jdbcType=VARCHAR},",
          "specify_zone_id_list = #{specifyZoneIdList,jdbcType=VARCHAR},",
          "specify_building_id_list = #{specifyBuildingIdList,jdbcType=VARCHAR},",
          "specify_unit_id_list = #{specifyUnitIdList,jdbcType=VARCHAR},",
          "specify_room_id_list = #{specifyRoomIdList,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where notice_id = #{noticeId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Notice record);
}