package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.Charge;
import com.highplace.biz.pm.domain.charge.ChargeExample.Criteria;
import com.highplace.biz.pm.domain.charge.ChargeExample.Criterion;
import com.highplace.biz.pm.domain.charge.ChargeExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class ChargeSqlProvider {

    public String countByExample(ChargeExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_charge");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(ChargeExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_charge");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Charge record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_charge");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getBillId() != null) {
            sql.VALUES("bill_id", "#{billId,jdbcType=BIGINT}");
        }
        
        if (record.getBillName() != null) {
            sql.VALUES("bill_name", "#{billName,jdbcType=BIGINT}");
        }
        
        if (record.getBillPeriod() != null) {
            sql.VALUES("bill_period", "#{billPeriod,jdbcType=VARCHAR}");
        }
        
        if (record.getBillDate() != null) {
            sql.VALUES("bill_date", "#{billDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getTotalAmount() != null) {
            sql.VALUES("total_amount", "#{totalAmount,jdbcType=DOUBLE}");
        }
        
        if (record.getReceivedAmount() != null) {
            sql.VALUES("received_amount", "#{receivedAmount,jdbcType=DOUBLE}");
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

    public String selectByExampleWithBLOBs(ChargeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("charge_id");
        } else {
            sql.SELECT("charge_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("bill_id");
        sql.SELECT("bill_name");
        sql.SELECT("bill_period");
        sql.SELECT("bill_date");
        sql.SELECT("status");
        sql.SELECT("total_amount");
        sql.SELECT("received_amount");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("remark");
        sql.FROM("t_charge");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(ChargeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("charge_id");
        } else {
            sql.SELECT("charge_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("bill_id");
        sql.SELECT("bill_name");
        sql.SELECT("bill_period");
        sql.SELECT("bill_date");
        sql.SELECT("status");
        sql.SELECT("total_amount");
        sql.SELECT("received_amount");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_charge");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Charge record = (Charge) parameter.get("record");
        ChargeExample example = (ChargeExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_charge");
        
        if (record.getChargeId() != null) {
            sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getBillId() != null) {
            sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        }
        
        if (record.getBillName() != null) {
            sql.SET("bill_name = #{record.billName,jdbcType=BIGINT}");
        }
        
        if (record.getBillPeriod() != null) {
            sql.SET("bill_period = #{record.billPeriod,jdbcType=VARCHAR}");
        }
        
        if (record.getBillDate() != null) {
            sql.SET("bill_date = #{record.billDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=INTEGER}");
        }
        
        if (record.getTotalAmount() != null) {
            sql.SET("total_amount = #{record.totalAmount,jdbcType=DOUBLE}");
        }
        
        if (record.getReceivedAmount() != null) {
            sql.SET("received_amount = #{record.receivedAmount,jdbcType=DOUBLE}");
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
        sql.UPDATE("t_charge");
        
        sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        sql.SET("bill_name = #{record.billName,jdbcType=BIGINT}");
        sql.SET("bill_period = #{record.billPeriod,jdbcType=VARCHAR}");
        sql.SET("bill_date = #{record.billDate,jdbcType=TIMESTAMP}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("total_amount = #{record.totalAmount,jdbcType=DOUBLE}");
        sql.SET("received_amount = #{record.receivedAmount,jdbcType=DOUBLE}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        ChargeExample example = (ChargeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge");
        
        sql.SET("charge_id = #{record.chargeId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        sql.SET("bill_name = #{record.billName,jdbcType=BIGINT}");
        sql.SET("bill_period = #{record.billPeriod,jdbcType=VARCHAR}");
        sql.SET("bill_date = #{record.billDate,jdbcType=TIMESTAMP}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("total_amount = #{record.totalAmount,jdbcType=DOUBLE}");
        sql.SET("received_amount = #{record.receivedAmount,jdbcType=DOUBLE}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        ChargeExample example = (ChargeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Charge record) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getBillId() != null) {
            sql.SET("bill_id = #{billId,jdbcType=BIGINT}");
        }
        
        if (record.getBillName() != null) {
            sql.SET("bill_name = #{billName,jdbcType=BIGINT}");
        }
        
        if (record.getBillPeriod() != null) {
            sql.SET("bill_period = #{billPeriod,jdbcType=VARCHAR}");
        }
        
        if (record.getBillDate() != null) {
            sql.SET("bill_date = #{billDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getTotalAmount() != null) {
            sql.SET("total_amount = #{totalAmount,jdbcType=DOUBLE}");
        }
        
        if (record.getReceivedAmount() != null) {
            sql.SET("received_amount = #{receivedAmount,jdbcType=DOUBLE}");
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
        
        sql.WHERE("charge_id = #{chargeId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, ChargeExample example, boolean includeExamplePhrase) {
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