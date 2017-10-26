package com.highplace.biz.pm.dao.org;

import com.highplace.biz.pm.domain.org.Department;
import com.highplace.biz.pm.domain.org.DepartmentExample.Criteria;
import com.highplace.biz.pm.domain.org.DepartmentExample.Criterion;
import com.highplace.biz.pm.domain.org.DepartmentExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class DepartmentSqlProvider {

    public String countByExample(DepartmentExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_department");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(DepartmentExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_department");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Department record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_department");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptName() != null) {
            sql.VALUES("dept_name", "#{deptName,jdbcType=VARCHAR}");
        }
        
        if (record.getLevel() != null) {
            sql.VALUES("level", "#{level,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getSuperiorDeptId() != null) {
            sql.VALUES("superior_dept_id", "#{superiorDeptId,jdbcType=BIGINT}");
        }
        
        if (record.getDeptCode() != null) {
            sql.VALUES("dept_code", "#{deptCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAliasName() != null) {
            sql.VALUES("alias_name", "#{aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.VALUES("modify_time", "#{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeptDesc() != null) {
            sql.VALUES("dept_desc", "#{deptDesc,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=LONGVARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExampleWithBLOBs(DepartmentExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("dept_id");
        } else {
            sql.SELECT("dept_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("dept_name");
        sql.SELECT("level");
        sql.SELECT("status");
        sql.SELECT("superior_dept_id");
        sql.SELECT("dept_code");
        sql.SELECT("alias_name");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("dept_desc");
        sql.SELECT("remark");
        sql.FROM("t_department");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(DepartmentExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("dept_id");
        } else {
            sql.SELECT("dept_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("dept_name");
        sql.SELECT("level");
        sql.SELECT("status");
        sql.SELECT("superior_dept_id");
        sql.SELECT("dept_code");
        sql.SELECT("alias_name");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_department");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Department record = (Department) parameter.get("record");
        DepartmentExample example = (DepartmentExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_department");
        
        if (record.getDeptId() != null) {
            sql.SET("dept_id = #{record.deptId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptName() != null) {
            sql.SET("dept_name = #{record.deptName,jdbcType=VARCHAR}");
        }
        
        if (record.getLevel() != null) {
            sql.SET("level = #{record.level,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=INTEGER}");
        }
        
        if (record.getSuperiorDeptId() != null) {
            sql.SET("superior_dept_id = #{record.superiorDeptId,jdbcType=BIGINT}");
        }
        
        if (record.getDeptCode() != null) {
            sql.SET("dept_code = #{record.deptCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAliasName() != null) {
            sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeptDesc() != null) {
            sql.SET("dept_desc = #{record.deptDesc,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExampleWithBLOBs(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_department");
        
        sql.SET("dept_id = #{record.deptId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("dept_name = #{record.deptName,jdbcType=VARCHAR}");
        sql.SET("level = #{record.level,jdbcType=INTEGER}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("superior_dept_id = #{record.superiorDeptId,jdbcType=BIGINT}");
        sql.SET("dept_code = #{record.deptCode,jdbcType=VARCHAR}");
        sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("dept_desc = #{record.deptDesc,jdbcType=LONGVARCHAR}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        DepartmentExample example = (DepartmentExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_department");
        
        sql.SET("dept_id = #{record.deptId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("dept_name = #{record.deptName,jdbcType=VARCHAR}");
        sql.SET("level = #{record.level,jdbcType=INTEGER}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("superior_dept_id = #{record.superiorDeptId,jdbcType=BIGINT}");
        sql.SET("dept_code = #{record.deptCode,jdbcType=VARCHAR}");
        sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        DepartmentExample example = (DepartmentExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Department record) {
        SQL sql = new SQL();
        sql.UPDATE("t_department");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptName() != null) {
            sql.SET("dept_name = #{deptName,jdbcType=VARCHAR}");
        }
        
        if (record.getLevel() != null) {
            sql.SET("level = #{level,jdbcType=INTEGER}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getSuperiorDeptId() != null) {
            sql.SET("superior_dept_id = #{superiorDeptId,jdbcType=BIGINT}");
        }
        
        if (record.getDeptCode() != null) {
            sql.SET("dept_code = #{deptCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAliasName() != null) {
            sql.SET("alias_name = #{aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeptDesc() != null) {
            sql.SET("dept_desc = #{deptDesc,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("dept_id = #{deptId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, DepartmentExample example, boolean includeExamplePhrase) {
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