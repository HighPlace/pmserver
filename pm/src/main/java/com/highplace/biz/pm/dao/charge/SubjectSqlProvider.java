package com.highplace.biz.pm.dao.charge;

import com.highplace.biz.pm.domain.charge.Subject;
import com.highplace.biz.pm.domain.charge.SubjectExample.Criteria;
import com.highplace.biz.pm.domain.charge.SubjectExample.Criterion;
import com.highplace.biz.pm.domain.charge.SubjectExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class SubjectSqlProvider {

    public String countByExample(SubjectExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_charge_subject");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(SubjectExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_charge_subject");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Subject record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_charge_subject");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubjectName() != null) {
            sql.VALUES("subject_name", "#{subjectName,jdbcType=VARCHAR}");
        }
        
        if (record.getChargeCycle() != null) {
            sql.VALUES("charge_cycle", "#{chargeCycle,jdbcType=INTEGER}");
        }
        
        if (record.getCycleFlag() != null) {
            sql.VALUES("cycle_flag", "#{cycleFlag,jdbcType=INTEGER}");
        }
        
        if (record.getLateFee() != null) {
            sql.VALUES("late_fee", "#{lateFee,jdbcType=DOUBLE}");
        }
        
        if (record.getRate() != null) {
            sql.VALUES("rate", "#{rate,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelOne() != null) {
            sql.VALUES("fee_level_one", "#{feeLevelOne,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelOneToplimit() != null) {
            sql.VALUES("level_one_toplimit", "#{levelOneToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelTwo() != null) {
            sql.VALUES("fee_level_two", "#{feeLevelTwo,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelTwoToplimit() != null) {
            sql.VALUES("level_two_toplimit", "#{levelTwoToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelThree() != null) {
            sql.VALUES("fee_level_three", "#{feeLevelThree,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelThreeToplimit() != null) {
            sql.VALUES("level_three_toplimit", "#{levelThreeToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeDataUnit() != null) {
            sql.VALUES("fee_data_unit", "#{feeDataUnit,jdbcType=VARCHAR}");
        }
        
        if (record.getFeeDataType() != null) {
            sql.VALUES("fee_data_type", "#{feeDataType,jdbcType=INTEGER}");
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

    public String selectByExampleWithBLOBs(SubjectExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("subject_id");
        } else {
            sql.SELECT("subject_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("subject_name");
        sql.SELECT("charge_cycle");
        sql.SELECT("cycle_flag");
        sql.SELECT("late_fee");
        sql.SELECT("rate");
        sql.SELECT("fee_level_one");
        sql.SELECT("level_one_toplimit");
        sql.SELECT("fee_level_two");
        sql.SELECT("level_two_toplimit");
        sql.SELECT("fee_level_three");
        sql.SELECT("level_three_toplimit");
        sql.SELECT("fee_data_unit");
        sql.SELECT("fee_data_type");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("remark");
        sql.FROM("t_charge_subject");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(SubjectExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("subject_id");
        } else {
            sql.SELECT("subject_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("subject_name");
        sql.SELECT("charge_cycle");
        sql.SELECT("cycle_flag");
        sql.SELECT("late_fee");
        sql.SELECT("rate");
        sql.SELECT("fee_level_one");
        sql.SELECT("level_one_toplimit");
        sql.SELECT("fee_level_two");
        sql.SELECT("level_two_toplimit");
        sql.SELECT("fee_level_three");
        sql.SELECT("level_three_toplimit");
        sql.SELECT("fee_data_unit");
        sql.SELECT("fee_data_type");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_charge_subject");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Subject record = (Subject) parameter.get("record");
        SubjectExample example = (SubjectExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_charge_subject");
        
        if (record.getSubjectId() != null) {
            sql.SET("subject_id = #{record.subjectId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubjectName() != null) {
            sql.SET("subject_name = #{record.subjectName,jdbcType=VARCHAR}");
        }
        
        if (record.getChargeCycle() != null) {
            sql.SET("charge_cycle = #{record.chargeCycle,jdbcType=INTEGER}");
        }
        
        if (record.getCycleFlag() != null) {
            sql.SET("cycle_flag = #{record.cycleFlag,jdbcType=INTEGER}");
        }
        
        if (record.getLateFee() != null) {
            sql.SET("late_fee = #{record.lateFee,jdbcType=DOUBLE}");
        }
        
        if (record.getRate() != null) {
            sql.SET("rate = #{record.rate,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelOne() != null) {
            sql.SET("fee_level_one = #{record.feeLevelOne,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelOneToplimit() != null) {
            sql.SET("level_one_toplimit = #{record.levelOneToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelTwo() != null) {
            sql.SET("fee_level_two = #{record.feeLevelTwo,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelTwoToplimit() != null) {
            sql.SET("level_two_toplimit = #{record.levelTwoToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelThree() != null) {
            sql.SET("fee_level_three = #{record.feeLevelThree,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelThreeToplimit() != null) {
            sql.SET("level_three_toplimit = #{record.levelThreeToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeDataUnit() != null) {
            sql.SET("fee_data_unit = #{record.feeDataUnit,jdbcType=VARCHAR}");
        }
        
        if (record.getFeeDataType() != null) {
            sql.SET("fee_data_type = #{record.feeDataType,jdbcType=INTEGER}");
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
        sql.UPDATE("t_charge_subject");
        
        sql.SET("subject_id = #{record.subjectId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("subject_name = #{record.subjectName,jdbcType=VARCHAR}");
        sql.SET("charge_cycle = #{record.chargeCycle,jdbcType=INTEGER}");
        sql.SET("cycle_flag = #{record.cycleFlag,jdbcType=INTEGER}");
        sql.SET("late_fee = #{record.lateFee,jdbcType=DOUBLE}");
        sql.SET("rate = #{record.rate,jdbcType=DOUBLE}");
        sql.SET("fee_level_one = #{record.feeLevelOne,jdbcType=DOUBLE}");
        sql.SET("level_one_toplimit = #{record.levelOneToplimit,jdbcType=DOUBLE}");
        sql.SET("fee_level_two = #{record.feeLevelTwo,jdbcType=DOUBLE}");
        sql.SET("level_two_toplimit = #{record.levelTwoToplimit,jdbcType=DOUBLE}");
        sql.SET("fee_level_three = #{record.feeLevelThree,jdbcType=DOUBLE}");
        sql.SET("level_three_toplimit = #{record.levelThreeToplimit,jdbcType=DOUBLE}");
        sql.SET("fee_data_unit = #{record.feeDataUnit,jdbcType=VARCHAR}");
        sql.SET("fee_data_type = #{record.feeDataType,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        SubjectExample example = (SubjectExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_subject");
        
        sql.SET("subject_id = #{record.subjectId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("subject_name = #{record.subjectName,jdbcType=VARCHAR}");
        sql.SET("charge_cycle = #{record.chargeCycle,jdbcType=INTEGER}");
        sql.SET("cycle_flag = #{record.cycleFlag,jdbcType=INTEGER}");
        sql.SET("late_fee = #{record.lateFee,jdbcType=DOUBLE}");
        sql.SET("rate = #{record.rate,jdbcType=DOUBLE}");
        sql.SET("fee_level_one = #{record.feeLevelOne,jdbcType=DOUBLE}");
        sql.SET("level_one_toplimit = #{record.levelOneToplimit,jdbcType=DOUBLE}");
        sql.SET("fee_level_two = #{record.feeLevelTwo,jdbcType=DOUBLE}");
        sql.SET("level_two_toplimit = #{record.levelTwoToplimit,jdbcType=DOUBLE}");
        sql.SET("fee_level_three = #{record.feeLevelThree,jdbcType=DOUBLE}");
        sql.SET("level_three_toplimit = #{record.levelThreeToplimit,jdbcType=DOUBLE}");
        sql.SET("fee_data_unit = #{record.feeDataUnit,jdbcType=VARCHAR}");
        sql.SET("fee_data_type = #{record.feeDataType,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        SubjectExample example = (SubjectExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Subject record) {
        SQL sql = new SQL();
        sql.UPDATE("t_charge_subject");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getSubjectName() != null) {
            sql.SET("subject_name = #{subjectName,jdbcType=VARCHAR}");
        }
        
        if (record.getChargeCycle() != null) {
            sql.SET("charge_cycle = #{chargeCycle,jdbcType=INTEGER}");
        }
        
        if (record.getCycleFlag() != null) {
            sql.SET("cycle_flag = #{cycleFlag,jdbcType=INTEGER}");
        }
        
        if (record.getLateFee() != null) {
            sql.SET("late_fee = #{lateFee,jdbcType=DOUBLE}");
        }
        
        if (record.getRate() != null) {
            sql.SET("rate = #{rate,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelOne() != null) {
            sql.SET("fee_level_one = #{feeLevelOne,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelOneToplimit() != null) {
            sql.SET("level_one_toplimit = #{levelOneToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelTwo() != null) {
            sql.SET("fee_level_two = #{feeLevelTwo,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelTwoToplimit() != null) {
            sql.SET("level_two_toplimit = #{levelTwoToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeLevelThree() != null) {
            sql.SET("fee_level_three = #{feeLevelThree,jdbcType=DOUBLE}");
        }
        
        if (record.getLevelThreeToplimit() != null) {
            sql.SET("level_three_toplimit = #{levelThreeToplimit,jdbcType=DOUBLE}");
        }
        
        if (record.getFeeDataUnit() != null) {
            sql.SET("fee_data_unit = #{feeDataUnit,jdbcType=VARCHAR}");
        }
        
        if (record.getFeeDataType() != null) {
            sql.SET("fee_data_type = #{feeDataType,jdbcType=INTEGER}");
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
        
        sql.WHERE("subject_id = #{subjectId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, SubjectExample example, boolean includeExamplePhrase) {
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