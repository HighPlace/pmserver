package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.Bill;
import com.highplace.biz.pm.domain.charge.BillExample.Criteria;
import com.highplace.biz.pm.domain.charge.BillExample.Criterion;
import com.highplace.biz.pm.domain.charge.BillExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class BillSqlProvider {

    public String countByExample(BillExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_charge_bill");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(BillExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_charge_bill");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Bill record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_charge_bill");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getBillName() != null) {
            sql.VALUES("bill_name", "#{billName,jdbcType=VARCHAR}");
        }
        
        if (record.getBillDay() != null) {
            sql.VALUES("bill_day", "#{billDay,jdbcType=INTEGER}");
        }
        
        if (record.getBillCycle() != null) {
            sql.VALUES("bill_cycle", "#{billCycle,jdbcType=INTEGER}");
        }
        
        if (record.getLastPayDay() != null) {
            sql.VALUES("last_pay_day", "#{lastPayDay,jdbcType=INTEGER}");
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

    public String selectByExampleWithBLOBs(BillExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("bill_id");
        } else {
            sql.SELECT("bill_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("bill_name");
        sql.SELECT("bill_day");
        sql.SELECT("bill_cycle");
        sql.SELECT("last_pay_day");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("remark");
        sql.FROM("t_charge_bill");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(BillExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("bill_id");
        } else {
            sql.SELECT("bill_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("bill_name");
        sql.SELECT("bill_day");
        sql.SELECT("bill_cycle");
        sql.SELECT("last_pay_day");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_charge_bill");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Bill record = (Bill) parameter.get("record");
        BillExample example = (BillExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_charge_bill");
        
        if (record.getBillId() != null) {
            sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getBillName() != null) {
            sql.SET("bill_name = #{record.billName,jdbcType=VARCHAR}");
        }
        
        if (record.getBillDay() != null) {
            sql.SET("bill_day = #{record.billDay,jdbcType=INTEGER}");
        }
        
        if (record.getBillCycle() != null) {
            sql.SET("bill_cycle = #{record.billCycle,jdbcType=INTEGER}");
        }
        
        if (record.getLastPayDay() != null) {
            sql.SET("last_pay_day = #{record.lastPayDay,jdbcType=INTEGER}");
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
        sql.UPDATE("t_charge_bill");
        
        sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("bill_name = #{record.billName,jdbcType=VARCHAR}");
        sql.SET("bill_day = #{record.billDay,jdbcType=INTEGER}");
        sql.SET("bill_cycle = #{record.billCycle,jdbcType=INTEGER}");
        sql.SET("last_pay_day = #{record.lastPayDay,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        BillExample example = (BillExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_bill");
        
        sql.SET("bill_id = #{record.billId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("bill_name = #{record.billName,jdbcType=VARCHAR}");
        sql.SET("bill_day = #{record.billDay,jdbcType=INTEGER}");
        sql.SET("bill_cycle = #{record.billCycle,jdbcType=INTEGER}");
        sql.SET("last_pay_day = #{record.lastPayDay,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        BillExample example = (BillExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Bill record) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_bill");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getBillName() != null) {
            sql.SET("bill_name = #{billName,jdbcType=VARCHAR}");
        }
        
        if (record.getBillDay() != null) {
            sql.SET("bill_day = #{billDay,jdbcType=INTEGER}");
        }
        
        if (record.getBillCycle() != null) {
            sql.SET("bill_cycle = #{billCycle,jdbcType=INTEGER}");
        }
        
        if (record.getLastPayDay() != null) {
            sql.SET("last_pay_day = #{lastPayDay,jdbcType=INTEGER}");
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
        
        sql.WHERE("bill_id = #{billId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, BillExample example, boolean includeExamplePhrase) {
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