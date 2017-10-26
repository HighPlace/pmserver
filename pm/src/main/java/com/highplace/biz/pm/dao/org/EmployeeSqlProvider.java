package com.highplace.biz.pm.dao.org;

import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.org.EmployeeExample.Criteria;
import com.highplace.biz.pm.domain.org.EmployeeExample.Criterion;
import com.highplace.biz.pm.domain.org.EmployeeExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class EmployeeSqlProvider {

    public String countByExample(EmployeeExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_employee");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(EmployeeExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_employee");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Employee record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_employee");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptId() != null) {
            sql.VALUES("dept_id", "#{deptId,jdbcType=BIGINT}");
        }
        
        if (record.getEmployeeName() != null) {
            sql.VALUES("employee_name", "#{employeeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getSysUsername() != null) {
            sql.VALUES("sys_username", "#{sysUsername,jdbcType=VARCHAR}");
        }
        
        if (record.getEmployeeCode() != null) {
            sql.VALUES("employee_code", "#{employeeCode,jdbcType=VARCHAR}");
        }
        
        if (record.getPosition() != null) {
            sql.VALUES("position", "#{position,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
        }
        
        if (record.getAliasName() != null) {
            sql.VALUES("alias_name", "#{aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentityType() != null) {
            sql.VALUES("identity_type", "#{identityType,jdbcType=INTEGER}");
        }
        
        if (record.getIdentityNo() != null) {
            sql.VALUES("identity_no", "#{identityNo,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentPic() != null) {
            sql.VALUES("ident_pic", "#{identPic,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.VALUES("email", "#{email,jdbcType=VARCHAR}");
        }
        
        if (record.getWechat() != null) {
            sql.VALUES("wechat", "#{wechat,jdbcType=VARCHAR}");
        }
        
        if (record.getBackupPhone1() != null) {
            sql.VALUES("backup_phone_1", "#{backupPhone1,jdbcType=VARCHAR}");
        }
        
        if (record.getBackupPhone2() != null) {
            sql.VALUES("backup_phone_2", "#{backupPhone2,jdbcType=VARCHAR}");
        }
        
        if (record.getEmergencyContactName() != null) {
            sql.VALUES("emergency_contact_name", "#{emergencyContactName,jdbcType=VARCHAR}");
        }
        
        if (record.getEmergencyContactPhone() != null) {
            sql.VALUES("emergency_contact_phone", "#{emergencyContactPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.VALUES("gender", "#{gender,jdbcType=VARCHAR}");
        }
        
        if (record.getEntryDate() != null) {
            sql.VALUES("entry_date", "#{entryDate,jdbcType=DATE}");
        }
        
        if (record.getLeaveDate() != null) {
            sql.VALUES("leave_date", "#{leaveDate,jdbcType=DATE}");
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

    public String selectByExampleWithBLOBs(EmployeeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("employee_id");
        } else {
            sql.SELECT("employee_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("dept_id");
        sql.SELECT("employee_name");
        sql.SELECT("phone");
        sql.SELECT("sys_username");
        sql.SELECT("employee_code");
        sql.SELECT("position");
        sql.SELECT("status");
        sql.SELECT("alias_name");
        sql.SELECT("identity_type");
        sql.SELECT("identity_no");
        sql.SELECT("ident_pic");
        sql.SELECT("email");
        sql.SELECT("wechat");
        sql.SELECT("backup_phone_1");
        sql.SELECT("backup_phone_2");
        sql.SELECT("emergency_contact_name");
        sql.SELECT("emergency_contact_phone");
        sql.SELECT("gender");
        sql.SELECT("entry_date");
        sql.SELECT("leave_date");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("remark");
        sql.FROM("t_employee");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(EmployeeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("employee_id");
        } else {
            sql.SELECT("employee_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("dept_id");
        sql.SELECT("employee_name");
        sql.SELECT("phone");
        sql.SELECT("sys_username");
        sql.SELECT("employee_code");
        sql.SELECT("position");
        sql.SELECT("status");
        sql.SELECT("alias_name");
        sql.SELECT("identity_type");
        sql.SELECT("identity_no");
        sql.SELECT("ident_pic");
        sql.SELECT("email");
        sql.SELECT("wechat");
        sql.SELECT("backup_phone_1");
        sql.SELECT("backup_phone_2");
        sql.SELECT("emergency_contact_name");
        sql.SELECT("emergency_contact_phone");
        sql.SELECT("gender");
        sql.SELECT("entry_date");
        sql.SELECT("leave_date");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_employee");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Employee record = (Employee) parameter.get("record");
        EmployeeExample example = (EmployeeExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_employee");
        
        if (record.getEmployeeId() != null) {
            sql.SET("employee_id = #{record.employeeId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptId() != null) {
            sql.SET("dept_id = #{record.deptId,jdbcType=BIGINT}");
        }
        
        if (record.getEmployeeName() != null) {
            sql.SET("employee_name = #{record.employeeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{record.phone,jdbcType=VARCHAR}");
        }
        
        if (record.getSysUsername() != null) {
            sql.SET("sys_username = #{record.sysUsername,jdbcType=VARCHAR}");
        }
        
        if (record.getEmployeeCode() != null) {
            sql.SET("employee_code = #{record.employeeCode,jdbcType=VARCHAR}");
        }
        
        if (record.getPosition() != null) {
            sql.SET("position = #{record.position,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=INTEGER}");
        }
        
        if (record.getAliasName() != null) {
            sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentityType() != null) {
            sql.SET("identity_type = #{record.identityType,jdbcType=INTEGER}");
        }
        
        if (record.getIdentityNo() != null) {
            sql.SET("identity_no = #{record.identityNo,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentPic() != null) {
            sql.SET("ident_pic = #{record.identPic,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        }
        
        if (record.getWechat() != null) {
            sql.SET("wechat = #{record.wechat,jdbcType=VARCHAR}");
        }
        
        if (record.getBackupPhone1() != null) {
            sql.SET("backup_phone_1 = #{record.backupPhone1,jdbcType=VARCHAR}");
        }
        
        if (record.getBackupPhone2() != null) {
            sql.SET("backup_phone_2 = #{record.backupPhone2,jdbcType=VARCHAR}");
        }
        
        if (record.getEmergencyContactName() != null) {
            sql.SET("emergency_contact_name = #{record.emergencyContactName,jdbcType=VARCHAR}");
        }
        
        if (record.getEmergencyContactPhone() != null) {
            sql.SET("emergency_contact_phone = #{record.emergencyContactPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.SET("gender = #{record.gender,jdbcType=VARCHAR}");
        }
        
        if (record.getEntryDate() != null) {
            sql.SET("entry_date = #{record.entryDate,jdbcType=DATE}");
        }
        
        if (record.getLeaveDate() != null) {
            sql.SET("leave_date = #{record.leaveDate,jdbcType=DATE}");
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
        sql.UPDATE("t_employee");
        
        sql.SET("employee_id = #{record.employeeId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("dept_id = #{record.deptId,jdbcType=BIGINT}");
        sql.SET("employee_name = #{record.employeeName,jdbcType=VARCHAR}");
        sql.SET("phone = #{record.phone,jdbcType=VARCHAR}");
        sql.SET("sys_username = #{record.sysUsername,jdbcType=VARCHAR}");
        sql.SET("employee_code = #{record.employeeCode,jdbcType=VARCHAR}");
        sql.SET("position = #{record.position,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        sql.SET("identity_type = #{record.identityType,jdbcType=INTEGER}");
        sql.SET("identity_no = #{record.identityNo,jdbcType=VARCHAR}");
        sql.SET("ident_pic = #{record.identPic,jdbcType=VARCHAR}");
        sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        sql.SET("wechat = #{record.wechat,jdbcType=VARCHAR}");
        sql.SET("backup_phone_1 = #{record.backupPhone1,jdbcType=VARCHAR}");
        sql.SET("backup_phone_2 = #{record.backupPhone2,jdbcType=VARCHAR}");
        sql.SET("emergency_contact_name = #{record.emergencyContactName,jdbcType=VARCHAR}");
        sql.SET("emergency_contact_phone = #{record.emergencyContactPhone,jdbcType=VARCHAR}");
        sql.SET("gender = #{record.gender,jdbcType=VARCHAR}");
        sql.SET("entry_date = #{record.entryDate,jdbcType=DATE}");
        sql.SET("leave_date = #{record.leaveDate,jdbcType=DATE}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        EmployeeExample example = (EmployeeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_employee");
        
        sql.SET("employee_id = #{record.employeeId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("dept_id = #{record.deptId,jdbcType=BIGINT}");
        sql.SET("employee_name = #{record.employeeName,jdbcType=VARCHAR}");
        sql.SET("phone = #{record.phone,jdbcType=VARCHAR}");
        sql.SET("sys_username = #{record.sysUsername,jdbcType=VARCHAR}");
        sql.SET("employee_code = #{record.employeeCode,jdbcType=VARCHAR}");
        sql.SET("position = #{record.position,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        sql.SET("identity_type = #{record.identityType,jdbcType=INTEGER}");
        sql.SET("identity_no = #{record.identityNo,jdbcType=VARCHAR}");
        sql.SET("ident_pic = #{record.identPic,jdbcType=VARCHAR}");
        sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        sql.SET("wechat = #{record.wechat,jdbcType=VARCHAR}");
        sql.SET("backup_phone_1 = #{record.backupPhone1,jdbcType=VARCHAR}");
        sql.SET("backup_phone_2 = #{record.backupPhone2,jdbcType=VARCHAR}");
        sql.SET("emergency_contact_name = #{record.emergencyContactName,jdbcType=VARCHAR}");
        sql.SET("emergency_contact_phone = #{record.emergencyContactPhone,jdbcType=VARCHAR}");
        sql.SET("gender = #{record.gender,jdbcType=VARCHAR}");
        sql.SET("entry_date = #{record.entryDate,jdbcType=DATE}");
        sql.SET("leave_date = #{record.leaveDate,jdbcType=DATE}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        EmployeeExample example = (EmployeeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Employee record) {
        SQL sql = new SQL();
        sql.UPDATE("t_employee");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getDeptId() != null) {
            sql.SET("dept_id = #{deptId,jdbcType=BIGINT}");
        }
        
        if (record.getEmployeeName() != null) {
            sql.SET("employee_name = #{employeeName,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getSysUsername() != null) {
            sql.SET("sys_username = #{sysUsername,jdbcType=VARCHAR}");
        }
        
        if (record.getEmployeeCode() != null) {
            sql.SET("employee_code = #{employeeCode,jdbcType=VARCHAR}");
        }
        
        if (record.getPosition() != null) {
            sql.SET("position = #{position,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
        }
        
        if (record.getAliasName() != null) {
            sql.SET("alias_name = #{aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentityType() != null) {
            sql.SET("identity_type = #{identityType,jdbcType=INTEGER}");
        }
        
        if (record.getIdentityNo() != null) {
            sql.SET("identity_no = #{identityNo,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentPic() != null) {
            sql.SET("ident_pic = #{identPic,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{email,jdbcType=VARCHAR}");
        }
        
        if (record.getWechat() != null) {
            sql.SET("wechat = #{wechat,jdbcType=VARCHAR}");
        }
        
        if (record.getBackupPhone1() != null) {
            sql.SET("backup_phone_1 = #{backupPhone1,jdbcType=VARCHAR}");
        }
        
        if (record.getBackupPhone2() != null) {
            sql.SET("backup_phone_2 = #{backupPhone2,jdbcType=VARCHAR}");
        }
        
        if (record.getEmergencyContactName() != null) {
            sql.SET("emergency_contact_name = #{emergencyContactName,jdbcType=VARCHAR}");
        }
        
        if (record.getEmergencyContactPhone() != null) {
            sql.SET("emergency_contact_phone = #{emergencyContactPhone,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.SET("gender = #{gender,jdbcType=VARCHAR}");
        }
        
        if (record.getEntryDate() != null) {
            sql.SET("entry_date = #{entryDate,jdbcType=DATE}");
        }
        
        if (record.getLeaveDate() != null) {
            sql.SET("leave_date = #{leaveDate,jdbcType=DATE}");
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
        
        sql.WHERE("employee_id = #{employeeId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, EmployeeExample example, boolean includeExamplePhrase) {
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