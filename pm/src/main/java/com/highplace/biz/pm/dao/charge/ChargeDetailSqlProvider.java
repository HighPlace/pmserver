package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.ChargeDetail;
import com.highplace.biz.pm.domain.charge.ChargeDetailExample.Criteria;
import com.highplace.biz.pm.domain.charge.ChargeDetailExample.Criterion;
import com.highplace.biz.pm.domain.charge.ChargeDetailExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class ChargeDetailSqlProvider {

    public String countByExample(ChargeDetailExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_charge_detail");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(ChargeDetailExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_charge_detail");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(ChargeDetail record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_charge_detail");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getChargeId() != null) {
            sql.VALUES("charge_id", "#{chargeId,jdbcType=BIGINT}");
        }
        
        if (record.getPropertyId() != null) {
            sql.VALUES("property_id", "#{propertyId,jdbcType=BIGINT}");
        }
        
        if (record.getAmount() != null) {
            sql.VALUES("amount", "#{amount,jdbcType=DOUBLE}");
        }
        
        if (record.getPayStatus() != null) {
            sql.VALUES("pay_status", "#{payStatus,jdbcType=INTEGER}");
        }
        
        if (record.getPayType() != null) {
            sql.VALUES("pay_type", "#{payType,jdbcType=INTEGER}");
        }
        
        if (record.getPayId() != null) {
            sql.VALUES("pay_id", "#{payId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getBillId() != null) {
            sql.VALUES("bill_id", "#{billId,jdbcType=BIGINT}");
        }
        
        if (record.getBillName() != null) {
            sql.VALUES("bill_name", "#{billName,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyName() != null) {
            sql.VALUES("property_name", "#{propertyName,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExampleWithBLOBs(ChargeDetailExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("detail_id");
        } else {
            sql.SELECT("detail_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("charge_id");
        sql.SELECT("property_id");
        sql.SELECT("amount");
        sql.SELECT("pay_status");
        sql.SELECT("pay_type");
        sql.SELECT("pay_id");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("bill_id");
        sql.SELECT("bill_name");
        sql.SELECT("property_name");
        sql.SELECT("remark");
        sql.FROM("t_charge_detail");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(ChargeDetailExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("detail_id");
        } else {
            sql.SELECT("detail_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("charge_id");
        sql.SELECT("property_id");
        sql.SELECT("amount");
        sql.SELECT("pay_status");
        sql.SELECT("pay_type");
        sql.SELECT("pay_id");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("bill_id");
        sql.SELECT("bill_name");
        sql.SELECT("property_name");
        sql.FROM("t_charge_detail");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        ChargeDetail record = (ChargeDetail) parameter.get("record");
        ChargeDetailExample example = (ChargeDetailExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_charge_detail");
        
        if (record.getDetailId() != null) {
            sql.SET("detail_id = #{record.detailId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getChargeId() != null) {
            sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        }
        
        if (record.getPropertyId() != null) {
            sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        }
        
        if (record.getAmount() != null) {
            sql.SET("amount = #{record.amount,jdbcType=DOUBLE}");
        }
        
        if (record.getPayStatus() != null) {
            sql.SET("pay_status = #{record.payStatus,jdbcType=INTEGER}");
        }
        
        if (record.getPayType() != null) {
            sql.SET("pay_type = #{record.payType,jdbcType=INTEGER}");
        }
        
        if (record.getPayId() != null) {
            sql.SET("pay_id = #{record.payId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getBillId() != null) {
            sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        }
        
        if (record.getBillName() != null) {
            sql.SET("bill_name = #{record.billName,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyName() != null) {
            sql.SET("property_name = #{record.propertyName,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_detail");
        
        sql.SET("detail_id = #{record.detailId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        sql.SET("amount = #{record.amount,jdbcType=DOUBLE}");
        sql.SET("pay_status = #{record.payStatus,jdbcType=INTEGER}");
        sql.SET("pay_type = #{record.payType,jdbcType=INTEGER}");
        sql.SET("pay_id = #{record.payId,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        sql.SET("bill_name = #{record.billName,jdbcType=VARCHAR}");
        sql.SET("property_name = #{record.propertyName,jdbcType=VARCHAR}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        ChargeDetailExample example = (ChargeDetailExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_detail");
        
        sql.SET("detail_id = #{record.detailId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        sql.SET("amount = #{record.amount,jdbcType=DOUBLE}");
        sql.SET("pay_status = #{record.payStatus,jdbcType=INTEGER}");
        sql.SET("pay_type = #{record.payType,jdbcType=INTEGER}");
        sql.SET("pay_id = #{record.payId,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        sql.SET("bill_name = #{record.billName,jdbcType=VARCHAR}");
        sql.SET("property_name = #{record.propertyName,jdbcType=VARCHAR}");
        
        ChargeDetailExample example = (ChargeDetailExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(ChargeDetail record) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_detail");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getChargeId() != null) {
            sql.SET("charge_id = #{chargeId,jdbcType=BIGINT}");
        }
        
        if (record.getPropertyId() != null) {
            sql.SET("property_id = #{propertyId,jdbcType=BIGINT}");
        }
        
        if (record.getAmount() != null) {
            sql.SET("amount = #{amount,jdbcType=DOUBLE}");
        }
        
        if (record.getPayStatus() != null) {
            sql.SET("pay_status = #{payStatus,jdbcType=INTEGER}");
        }
        
        if (record.getPayType() != null) {
            sql.SET("pay_type = #{payType,jdbcType=INTEGER}");
        }
        
        if (record.getPayId() != null) {
            sql.SET("pay_id = #{payId,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getBillId() != null) {
            sql.SET("bill_id = #{billId,jdbcType=BIGINT}");
        }
        
        if (record.getBillName() != null) {
            sql.SET("bill_name = #{billName,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyName() != null) {
            sql.SET("property_name = #{propertyName,jdbcType=VARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("detail_id = #{detailId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, ChargeDetailExample example, boolean includeExamplePhrase) {
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