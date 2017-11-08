package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.ChargeWater;
import com.highplace.biz.pm.domain.charge.ChargeWaterExample.Criteria;
import com.highplace.biz.pm.domain.charge.ChargeWaterExample.Criterion;
import com.highplace.biz.pm.domain.charge.ChargeWaterExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class ChargeWaterSqlProvider {

    public String countByExample(ChargeWaterExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_charge_water");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(ChargeWaterExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_charge_water");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(ChargeWater record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_charge_water");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getChargeId() != null) {
            sql.VALUES("charge_id", "#{chargeId,jdbcType=BIGINT}");
        }
        
        if (record.getPropertyId() != null) {
            sql.VALUES("property_id", "#{propertyId,jdbcType=BIGINT}");
        }
        
        if (record.getFeeDataType() != null) {
            sql.VALUES("fee_data_type", "#{feeDataType,jdbcType=INTEGER}");
        }
        
        if (record.getPropertyName() != null) {
            sql.VALUES("property_name", "#{propertyName,jdbcType=VARCHAR}");
        }
        
        if (record.getBeginDate() != null) {
            sql.VALUES("begin_date", "#{beginDate,jdbcType=DATE}");
        }
        
        if (record.getEndDate() != null) {
            sql.VALUES("end_date", "#{endDate,jdbcType=DATE}");
        }
        
        if (record.getBeginUsage() != null) {
            sql.VALUES("begin_usage", "#{beginUsage,jdbcType=DOUBLE}");
        }
        
        if (record.getEndUsage() != null) {
            sql.VALUES("end_usage", "#{endUsage,jdbcType=DOUBLE}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExampleWithBLOBs(ChargeWaterExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("product_inst_id");
        } else {
            sql.SELECT("product_inst_id");
        }
        sql.SELECT("charge_id");
        sql.SELECT("property_id");
        sql.SELECT("fee_data_type");
        sql.SELECT("property_name");
        sql.SELECT("begin_date");
        sql.SELECT("end_date");
        sql.SELECT("begin_usage");
        sql.SELECT("end_usage");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("remark");
        sql.FROM("t_charge_water");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(ChargeWaterExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("product_inst_id");
        } else {
            sql.SELECT("product_inst_id");
        }
        sql.SELECT("charge_id");
        sql.SELECT("property_id");
        sql.SELECT("fee_data_type");
        sql.SELECT("property_name");
        sql.SELECT("begin_date");
        sql.SELECT("end_date");
        sql.SELECT("begin_usage");
        sql.SELECT("end_usage");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_charge_water");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        ChargeWater record = (ChargeWater) parameter.get("record");
        ChargeWaterExample example = (ChargeWaterExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_charge_water");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getChargeId() != null) {
            sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        }
        
        if (record.getPropertyId() != null) {
            sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        }
        
        if (record.getFeeDataType() != null) {
            sql.SET("fee_data_type = #{record.feeDataType,jdbcType=INTEGER}");
        }
        
        if (record.getPropertyName() != null) {
            sql.SET("property_name = #{record.propertyName,jdbcType=VARCHAR}");
        }
        
        if (record.getBeginDate() != null) {
            sql.SET("begin_date = #{record.beginDate,jdbcType=DATE}");
        }
        
        if (record.getEndDate() != null) {
            sql.SET("end_date = #{record.endDate,jdbcType=DATE}");
        }
        
        if (record.getBeginUsage() != null) {
            sql.SET("begin_usage = #{record.beginUsage,jdbcType=DOUBLE}");
        }
        
        if (record.getEndUsage() != null) {
            sql.SET("end_usage = #{record.endUsage,jdbcType=DOUBLE}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_water");
        
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        sql.SET("fee_data_type = #{record.feeDataType,jdbcType=INTEGER}");
        sql.SET("property_name = #{record.propertyName,jdbcType=VARCHAR}");
        sql.SET("begin_date = #{record.beginDate,jdbcType=DATE}");
        sql.SET("end_date = #{record.endDate,jdbcType=DATE}");
        sql.SET("begin_usage = #{record.beginUsage,jdbcType=DOUBLE}");
        sql.SET("end_usage = #{record.endUsage,jdbcType=DOUBLE}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        ChargeWaterExample example = (ChargeWaterExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_water");
        
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        sql.SET("fee_data_type = #{record.feeDataType,jdbcType=INTEGER}");
        sql.SET("property_name = #{record.propertyName,jdbcType=VARCHAR}");
        sql.SET("begin_date = #{record.beginDate,jdbcType=DATE}");
        sql.SET("end_date = #{record.endDate,jdbcType=DATE}");
        sql.SET("begin_usage = #{record.beginUsage,jdbcType=DOUBLE}");
        sql.SET("end_usage = #{record.endUsage,jdbcType=DOUBLE}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        ChargeWaterExample example = (ChargeWaterExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(ChargeWater record) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_water");
        
        if (record.getPropertyName() != null) {
            sql.SET("property_name = #{propertyName,jdbcType=VARCHAR}");
        }
        
        if (record.getBeginDate() != null) {
            sql.SET("begin_date = #{beginDate,jdbcType=DATE}");
        }
        
        if (record.getEndDate() != null) {
            sql.SET("end_date = #{endDate,jdbcType=DATE}");
        }
        
        if (record.getBeginUsage() != null) {
            sql.SET("begin_usage = #{beginUsage,jdbcType=DOUBLE}");
        }
        
        if (record.getEndUsage() != null) {
            sql.SET("end_usage = #{endUsage,jdbcType=DOUBLE}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        sql.WHERE("charge_id = #{chargeId,jdbcType=BIGINT}");
        sql.WHERE("property_id = #{propertyId,jdbcType=BIGINT}");
        sql.WHERE("fee_data_type = #{feeDataType,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, ChargeWaterExample example, boolean includeExamplePhrase) {
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