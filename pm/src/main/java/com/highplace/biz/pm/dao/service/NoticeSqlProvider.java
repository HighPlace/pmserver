package com.highplace.biz.pm.dao.service;

import com.highplace.biz.pm.domain.service.Notice;
import com.highplace.biz.pm.domain.service.NoticeExample.Criteria;
import com.highplace.biz.pm.domain.service.NoticeExample.Criterion;
import com.highplace.biz.pm.domain.service.NoticeExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class NoticeSqlProvider {

    public String countByExample(NoticeExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_notice");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(NoticeExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_notice");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Notice record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_notice");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.VALUES("title", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getPublishDate() != null) {
            sql.VALUES("publish_date", "#{publishDate,jdbcType=DATE}");
        }
        
        if (record.getValidDate() != null) {
            sql.VALUES("valid_date", "#{validDate,jdbcType=DATE}");
        }
        
        if (record.getPublisher() != null) {
            sql.VALUES("publisher", "#{publisher,jdbcType=VARCHAR}");
        }
        
        if (record.getApprover() != null) {
            sql.VALUES("approver", "#{approver,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachmentName() != null) {
            sql.VALUES("attachment_name", "#{attachmentName,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachmentLink() != null) {
            sql.VALUES("attachment_link", "#{attachmentLink,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyZoneIdList() != null) {
            sql.VALUES("specify_zone_id_list", "#{specifyZoneIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyBuildingIdList() != null) {
            sql.VALUES("specify_building_id_list", "#{specifyBuildingIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyUnitIdList() != null) {
            sql.VALUES("specify_unit_id_list", "#{specifyUnitIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyRoomIdList() != null) {
            sql.VALUES("specify_room_id_list", "#{specifyRoomIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getContent() != null) {
            sql.VALUES("content", "#{content,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExampleWithBLOBs(NoticeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("notice_id");
        } else {
            sql.SELECT("notice_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("title");
        sql.SELECT("type");
        sql.SELECT("status");
        sql.SELECT("publish_date");
        sql.SELECT("valid_date");
        sql.SELECT("publisher");
        sql.SELECT("approver");
        sql.SELECT("attachment_name");
        sql.SELECT("attachment_link");
        sql.SELECT("specify_zone_id_list");
        sql.SELECT("specify_building_id_list");
        sql.SELECT("specify_unit_id_list");
        sql.SELECT("specify_room_id_list");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("content");
        sql.SELECT("remark");
        sql.FROM("t_notice");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(NoticeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("notice_id");
        } else {
            sql.SELECT("notice_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("title");
        sql.SELECT("type");
        sql.SELECT("status");
        sql.SELECT("publish_date");
        sql.SELECT("valid_date");
        sql.SELECT("publisher");
        sql.SELECT("approver");
        sql.SELECT("attachment_name");
        sql.SELECT("attachment_link");
        sql.SELECT("specify_zone_id_list");
        sql.SELECT("specify_building_id_list");
        sql.SELECT("specify_unit_id_list");
        sql.SELECT("specify_room_id_list");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_notice");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Notice record = (Notice) parameter.get("record");
        NoticeExample example = (NoticeExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_notice");
        
        if (record.getNoticeId() != null) {
            sql.SET("notice_id = #{record.noticeId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.SET("title = #{record.title,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{record.type,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=INTEGER}");
        }
        
        if (record.getPublishDate() != null) {
            sql.SET("publish_date = #{record.publishDate,jdbcType=DATE}");
        }
        
        if (record.getValidDate() != null) {
            sql.SET("valid_date = #{record.validDate,jdbcType=DATE}");
        }
        
        if (record.getPublisher() != null) {
            sql.SET("publisher = #{record.publisher,jdbcType=VARCHAR}");
        }
        
        if (record.getApprover() != null) {
            sql.SET("approver = #{record.approver,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachmentName() != null) {
            sql.SET("attachment_name = #{record.attachmentName,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachmentLink() != null) {
            sql.SET("attachment_link = #{record.attachmentLink,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyZoneIdList() != null) {
            sql.SET("specify_zone_id_list = #{record.specifyZoneIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyBuildingIdList() != null) {
            sql.SET("specify_building_id_list = #{record.specifyBuildingIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyUnitIdList() != null) {
            sql.SET("specify_unit_id_list = #{record.specifyUnitIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyRoomIdList() != null) {
            sql.SET("specify_room_id_list = #{record.specifyRoomIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getContent() != null) {
            sql.SET("content = #{record.content,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_notice");
        
        sql.SET("notice_id = #{record.noticeId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("title = #{record.title,jdbcType=VARCHAR}");
        sql.SET("type = #{record.type,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("publish_date = #{record.publishDate,jdbcType=DATE}");
        sql.SET("valid_date = #{record.validDate,jdbcType=DATE}");
        sql.SET("publisher = #{record.publisher,jdbcType=VARCHAR}");
        sql.SET("approver = #{record.approver,jdbcType=VARCHAR}");
        sql.SET("attachment_name = #{record.attachmentName,jdbcType=VARCHAR}");
        sql.SET("attachment_link = #{record.attachmentLink,jdbcType=VARCHAR}");
        sql.SET("specify_zone_id_list = #{record.specifyZoneIdList,jdbcType=VARCHAR}");
        sql.SET("specify_building_id_list = #{record.specifyBuildingIdList,jdbcType=VARCHAR}");
        sql.SET("specify_unit_id_list = #{record.specifyUnitIdList,jdbcType=VARCHAR}");
        sql.SET("specify_room_id_list = #{record.specifyRoomIdList,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("content = #{record.content,jdbcType=LONGVARCHAR}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        NoticeExample example = (NoticeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_notice");
        
        sql.SET("notice_id = #{record.noticeId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("title = #{record.title,jdbcType=VARCHAR}");
        sql.SET("type = #{record.type,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("publish_date = #{record.publishDate,jdbcType=DATE}");
        sql.SET("valid_date = #{record.validDate,jdbcType=DATE}");
        sql.SET("publisher = #{record.publisher,jdbcType=VARCHAR}");
        sql.SET("approver = #{record.approver,jdbcType=VARCHAR}");
        sql.SET("attachment_name = #{record.attachmentName,jdbcType=VARCHAR}");
        sql.SET("attachment_link = #{record.attachmentLink,jdbcType=VARCHAR}");
        sql.SET("specify_zone_id_list = #{record.specifyZoneIdList,jdbcType=VARCHAR}");
        sql.SET("specify_building_id_list = #{record.specifyBuildingIdList,jdbcType=VARCHAR}");
        sql.SET("specify_unit_id_list = #{record.specifyUnitIdList,jdbcType=VARCHAR}");
        sql.SET("specify_room_id_list = #{record.specifyRoomIdList,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        NoticeExample example = (NoticeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Notice record) {
        SQL sql = new SQL();
        sql.UPDATE("t_notice");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.SET("title = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getPublishDate() != null) {
            sql.SET("publish_date = #{publishDate,jdbcType=DATE}");
        }
        
        if (record.getValidDate() != null) {
            sql.SET("valid_date = #{validDate,jdbcType=DATE}");
        }
        
        if (record.getPublisher() != null) {
            sql.SET("publisher = #{publisher,jdbcType=VARCHAR}");
        }
        
        if (record.getApprover() != null) {
            sql.SET("approver = #{approver,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachmentName() != null) {
            sql.SET("attachment_name = #{attachmentName,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachmentLink() != null) {
            sql.SET("attachment_link = #{attachmentLink,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyZoneIdList() != null) {
            sql.SET("specify_zone_id_list = #{specifyZoneIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyBuildingIdList() != null) {
            sql.SET("specify_building_id_list = #{specifyBuildingIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyUnitIdList() != null) {
            sql.SET("specify_unit_id_list = #{specifyUnitIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getSpecifyRoomIdList() != null) {
            sql.SET("specify_room_id_list = #{specifyRoomIdList,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getContent() != null) {
            sql.SET("content = #{content,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("notice_id = #{noticeId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, NoticeExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}