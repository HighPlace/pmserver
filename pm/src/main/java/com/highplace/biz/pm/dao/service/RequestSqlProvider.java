package com.highplace.biz.pm.dao.service;

import com.highplace.biz.pm.domain.service.Request;
import com.highplace.biz.pm.domain.service.RequestExample.Criteria;
import com.highplace.biz.pm.domain.service.RequestExample.Criterion;
import com.highplace.biz.pm.domain.service.RequestExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class RequestSqlProvider {

    public String countByExample(RequestExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_request");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(RequestExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_request");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Request record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_request");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=VARCHAR}");
        }
        
        if (record.getSubType() != null) {
            sql.VALUES("sub_type", "#{subType,jdbcType=VARCHAR}");
        }
        
        if (record.getSource() != null) {
            sql.VALUES("source", "#{source,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getAttachment1() != null) {
            sql.VALUES("attachment1", "#{attachment1,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachment2() != null) {
            sql.VALUES("attachment2", "#{attachment2,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyId() != null) {
            sql.VALUES("property_id", "#{propertyId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubmitter() != null) {
            sql.VALUES("submitter", "#{submitter,jdbcType=VARCHAR}");
        }
        
        if (record.getContactInfo() != null) {
            sql.VALUES("contact_info", "#{contactInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.VALUES("priority", "#{priority,jdbcType=INTEGER}");
        }
        
        if (record.getStartTime() != null) {
            sql.VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAssignTime() != null) {
            sql.VALUES("assign_time", "#{assignTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAcceptTime() != null) {
            sql.VALUES("accept_time", "#{acceptTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFinishTime() != null) {
            sql.VALUES("finish_time", "#{finishTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRateTime() != null) {
            sql.VALUES("rate_time", "#{rateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDealEmployeeId() != null) {
            sql.VALUES("deal_employee_id", "#{dealEmployeeId,jdbcType=BIGINT}");
        }
        
        if (record.getDealDesc() != null) {
            sql.VALUES("deal_desc", "#{dealDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getRateLevel() != null) {
            sql.VALUES("rate_level", "#{rateLevel,jdbcType=INTEGER}");
        }
        
        if (record.getRateNum() != null) {
            sql.VALUES("rate_num", "#{rateNum,jdbcType=INTEGER}");
        }
        
        if (record.getRateAttachment() != null) {
            sql.VALUES("rate_attachment", "#{rateAttachment,jdbcType=VARCHAR}");
        }
        
        if (record.getRateUsername() != null) {
            sql.VALUES("rate_username", "#{rateUsername,jdbcType=VARCHAR}");
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
        
        if (record.getRateDesc() != null) {
            sql.VALUES("rate_desc", "#{rateDesc,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExampleWithBLOBs(RequestExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("request_id");
        } else {
            sql.SELECT("request_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("type");
        sql.SELECT("sub_type");
        sql.SELECT("source");
        sql.SELECT("status");
        sql.SELECT("attachment1");
        sql.SELECT("attachment2");
        sql.SELECT("property_id");
        sql.SELECT("submitter");
        sql.SELECT("contact_info");
        sql.SELECT("priority");
        sql.SELECT("start_time");
        sql.SELECT("assign_time");
        sql.SELECT("accept_time");
        sql.SELECT("finish_time");
        sql.SELECT("rate_time");
        sql.SELECT("deal_employee_id");
        sql.SELECT("deal_desc");
        sql.SELECT("rate_level");
        sql.SELECT("rate_num");
        sql.SELECT("rate_attachment");
        sql.SELECT("rate_username");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("content");
        sql.SELECT("rate_desc");
        sql.SELECT("remark");
        sql.FROM("t_request");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(RequestExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("request_id");
        } else {
            sql.SELECT("request_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("type");
        sql.SELECT("sub_type");
        sql.SELECT("source");
        sql.SELECT("status");
        sql.SELECT("attachment1");
        sql.SELECT("attachment2");
        sql.SELECT("property_id");
        sql.SELECT("submitter");
        sql.SELECT("contact_info");
        sql.SELECT("priority");
        sql.SELECT("start_time");
        sql.SELECT("assign_time");
        sql.SELECT("accept_time");
        sql.SELECT("finish_time");
        sql.SELECT("rate_time");
        sql.SELECT("deal_employee_id");
        sql.SELECT("deal_desc");
        sql.SELECT("rate_level");
        sql.SELECT("rate_num");
        sql.SELECT("rate_attachment");
        sql.SELECT("rate_username");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_request");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Request record = (Request) parameter.get("record");
        RequestExample example = (RequestExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_request");
        
        if (record.getRequestId() != null) {
            sql.SET("request_id = #{record.requestId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{record.type,jdbcType=VARCHAR}");
        }
        
        if (record.getSubType() != null) {
            sql.SET("sub_type = #{record.subType,jdbcType=VARCHAR}");
        }
        
        if (record.getSource() != null) {
            sql.SET("source = #{record.source,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=INTEGER}");
        }
        
        if (record.getAttachment1() != null) {
            sql.SET("attachment1 = #{record.attachment1,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachment2() != null) {
            sql.SET("attachment2 = #{record.attachment2,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyId() != null) {
            sql.SET("property_id = #{record.propertyId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubmitter() != null) {
            sql.SET("submitter = #{record.submitter,jdbcType=VARCHAR}");
        }
        
        if (record.getContactInfo() != null) {
            sql.SET("contact_info = #{record.contactInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.SET("priority = #{record.priority,jdbcType=INTEGER}");
        }
        
        if (record.getStartTime() != null) {
            sql.SET("start_time = #{record.startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAssignTime() != null) {
            sql.SET("assign_time = #{record.assignTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAcceptTime() != null) {
            sql.SET("accept_time = #{record.acceptTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFinishTime() != null) {
            sql.SET("finish_time = #{record.finishTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRateTime() != null) {
            sql.SET("rate_time = #{record.rateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDealEmployeeId() != null) {
            sql.SET("deal_employee_id = #{record.dealEmployeeId,jdbcType=BIGINT}");
        }
        
        if (record.getDealDesc() != null) {
            sql.SET("deal_desc = #{record.dealDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getRateLevel() != null) {
            sql.SET("rate_level = #{record.rateLevel,jdbcType=INTEGER}");
        }
        
        if (record.getRateNum() != null) {
            sql.SET("rate_num = #{record.rateNum,jdbcType=INTEGER}");
        }
        
        if (record.getRateAttachment() != null) {
            sql.SET("rate_attachment = #{record.rateAttachment,jdbcType=VARCHAR}");
        }
        
        if (record.getRateUsername() != null) {
            sql.SET("rate_username = #{record.rateUsername,jdbcType=VARCHAR}");
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
        
        if (record.getRateDesc() != null) {
            sql.SET("rate_desc = #{record.rateDesc,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_request");
        
        sql.SET("request_id = #{record.requestId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("type = #{record.type,jdbcType=VARCHAR}");
        sql.SET("sub_type = #{record.subType,jdbcType=VARCHAR}");
        sql.SET("source = #{record.source,jdbcType=INTEGER}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("attachment1 = #{record.attachment1,jdbcType=VARCHAR}");
        sql.SET("attachment2 = #{record.attachment2,jdbcType=VARCHAR}");
        sql.SET("property_id = #{record.propertyId,jdbcType=VARCHAR}");
        sql.SET("submitter = #{record.submitter,jdbcType=VARCHAR}");
        sql.SET("contact_info = #{record.contactInfo,jdbcType=VARCHAR}");
        sql.SET("priority = #{record.priority,jdbcType=INTEGER}");
        sql.SET("start_time = #{record.startTime,jdbcType=TIMESTAMP}");
        sql.SET("assign_time = #{record.assignTime,jdbcType=TIMESTAMP}");
        sql.SET("accept_time = #{record.acceptTime,jdbcType=TIMESTAMP}");
        sql.SET("finish_time = #{record.finishTime,jdbcType=TIMESTAMP}");
        sql.SET("rate_time = #{record.rateTime,jdbcType=TIMESTAMP}");
        sql.SET("deal_employee_id = #{record.dealEmployeeId,jdbcType=BIGINT}");
        sql.SET("deal_desc = #{record.dealDesc,jdbcType=VARCHAR}");
        sql.SET("rate_level = #{record.rateLevel,jdbcType=INTEGER}");
        sql.SET("rate_num = #{record.rateNum,jdbcType=INTEGER}");
        sql.SET("rate_attachment = #{record.rateAttachment,jdbcType=VARCHAR}");
        sql.SET("rate_username = #{record.rateUsername,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("content = #{record.content,jdbcType=LONGVARCHAR}");
        sql.SET("rate_desc = #{record.rateDesc,jdbcType=LONGVARCHAR}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        RequestExample example = (RequestExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_request");
        
        sql.SET("request_id = #{record.requestId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("type = #{record.type,jdbcType=VARCHAR}");
        sql.SET("sub_type = #{record.subType,jdbcType=VARCHAR}");
        sql.SET("source = #{record.source,jdbcType=INTEGER}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("attachment1 = #{record.attachment1,jdbcType=VARCHAR}");
        sql.SET("attachment2 = #{record.attachment2,jdbcType=VARCHAR}");
        sql.SET("property_id = #{record.propertyId,jdbcType=VARCHAR}");
        sql.SET("submitter = #{record.submitter,jdbcType=VARCHAR}");
        sql.SET("contact_info = #{record.contactInfo,jdbcType=VARCHAR}");
        sql.SET("priority = #{record.priority,jdbcType=INTEGER}");
        sql.SET("start_time = #{record.startTime,jdbcType=TIMESTAMP}");
        sql.SET("assign_time = #{record.assignTime,jdbcType=TIMESTAMP}");
        sql.SET("accept_time = #{record.acceptTime,jdbcType=TIMESTAMP}");
        sql.SET("finish_time = #{record.finishTime,jdbcType=TIMESTAMP}");
        sql.SET("rate_time = #{record.rateTime,jdbcType=TIMESTAMP}");
        sql.SET("deal_employee_id = #{record.dealEmployeeId,jdbcType=BIGINT}");
        sql.SET("deal_desc = #{record.dealDesc,jdbcType=VARCHAR}");
        sql.SET("rate_level = #{record.rateLevel,jdbcType=INTEGER}");
        sql.SET("rate_num = #{record.rateNum,jdbcType=INTEGER}");
        sql.SET("rate_attachment = #{record.rateAttachment,jdbcType=VARCHAR}");
        sql.SET("rate_username = #{record.rateUsername,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        RequestExample example = (RequestExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Request record) {
        SQL sql = new SQL();
        sql.UPDATE("t_request");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=VARCHAR}");
        }
        
        if (record.getSubType() != null) {
            sql.SET("sub_type = #{subType,jdbcType=VARCHAR}");
        }
        
        if (record.getSource() != null) {
            sql.SET("source = #{source,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getAttachment1() != null) {
            sql.SET("attachment1 = #{attachment1,jdbcType=VARCHAR}");
        }
        
        if (record.getAttachment2() != null) {
            sql.SET("attachment2 = #{attachment2,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyId() != null) {
            sql.SET("property_id = #{propertyId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubmitter() != null) {
            sql.SET("submitter = #{submitter,jdbcType=VARCHAR}");
        }
        
        if (record.getContactInfo() != null) {
            sql.SET("contact_info = #{contactInfo,jdbcType=VARCHAR}");
        }
        
        if (record.getPriority() != null) {
            sql.SET("priority = #{priority,jdbcType=INTEGER}");
        }
        
        if (record.getStartTime() != null) {
            sql.SET("start_time = #{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAssignTime() != null) {
            sql.SET("assign_time = #{assignTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getAcceptTime() != null) {
            sql.SET("accept_time = #{acceptTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getFinishTime() != null) {
            sql.SET("finish_time = #{finishTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRateTime() != null) {
            sql.SET("rate_time = #{rateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDealEmployeeId() != null) {
            sql.SET("deal_employee_id = #{dealEmployeeId,jdbcType=BIGINT}");
        }
        
        if (record.getDealDesc() != null) {
            sql.SET("deal_desc = #{dealDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getRateLevel() != null) {
            sql.SET("rate_level = #{rateLevel,jdbcType=INTEGER}");
        }
        
        if (record.getRateNum() != null) {
            sql.SET("rate_num = #{rateNum,jdbcType=INTEGER}");
        }
        
        if (record.getRateAttachment() != null) {
            sql.SET("rate_attachment = #{rateAttachment,jdbcType=VARCHAR}");
        }
        
        if (record.getRateUsername() != null) {
            sql.SET("rate_username = #{rateUsername,jdbcType=VARCHAR}");
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
        
        if (record.getRateDesc() != null) {
            sql.SET("rate_desc = #{rateDesc,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("request_id = #{requestId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, RequestExample example, boolean includeExamplePhrase) {
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