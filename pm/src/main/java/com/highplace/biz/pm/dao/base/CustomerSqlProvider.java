package com.highplace.biz.pm.dao.base;

import com.highplace.biz.pm.domain.base.Customer;
import com.highplace.biz.pm.domain.base.CustomerExample.Criteria;
import com.highplace.biz.pm.domain.base.CustomerExample.Criterion;
import com.highplace.biz.pm.domain.base.CustomerExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class CustomerSqlProvider {

    public String countByExample(CustomerExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_customer");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(CustomerExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_customer");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Customer record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_customer");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerName() != null) {
            sql.VALUES("customer_name", "#{customerName,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentityType() != null) {
            sql.VALUES("identity_type", "#{identityType,jdbcType=INTEGER}");
        }
        
        if (record.getIdentityNo() != null) {
            sql.VALUES("identity_no", "#{identityNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.VALUES("phone", "#{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getAliasName() != null) {
            sql.VALUES("alias_name", "#{aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.VALUES("email", "#{email,jdbcType=VARCHAR}");
        }
        
        if (record.getWechat() != null) {
            sql.VALUES("wechat", "#{wechat,jdbcType=VARCHAR}");
        }
        
        if (record.getLang() != null) {
            sql.VALUES("lang", "#{lang,jdbcType=VARCHAR}");
        }
        
        if (record.getNation() != null) {
            sql.VALUES("nation", "#{nation,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.VALUES("gender", "#{gender,jdbcType=VARCHAR}");
        }
        
        if (record.getBirth() != null) {
            sql.VALUES("birth", "#{birth,jdbcType=DATE}");
        }
        
        if (record.getIdentStartDate() != null) {
            sql.VALUES("ident_start_date", "#{identStartDate,jdbcType=DATE}");
        }
        
        if (record.getIdentEndDate() != null) {
            sql.VALUES("ident_end_date", "#{identEndDate,jdbcType=DATE}");
        }
        
        if (record.getIdentPic() != null) {
            sql.VALUES("ident_pic", "#{identPic,jdbcType=VARCHAR}");
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

    public String selectByExampleWithBLOBs(CustomerExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("customer_id");
        } else {
            sql.SELECT("customer_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("customer_name");
        sql.SELECT("identity_type");
        sql.SELECT("identity_no");
        sql.SELECT("phone");
        sql.SELECT("alias_name");
        sql.SELECT("email");
        sql.SELECT("wechat");
        sql.SELECT("lang");
        sql.SELECT("nation");
        sql.SELECT("gender");
        sql.SELECT("birth");
        sql.SELECT("ident_start_date");
        sql.SELECT("ident_end_date");
        sql.SELECT("ident_pic");
        sql.SELECT("backup_phone_1");
        sql.SELECT("backup_phone_2");
        sql.SELECT("emergency_contact_name");
        sql.SELECT("emergency_contact_phone");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("remark");
        sql.FROM("t_customer");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(CustomerExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("customer_id");
        } else {
            sql.SELECT("customer_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("customer_name");
        sql.SELECT("identity_type");
        sql.SELECT("identity_no");
        sql.SELECT("phone");
        sql.SELECT("alias_name");
        sql.SELECT("email");
        sql.SELECT("wechat");
        sql.SELECT("lang");
        sql.SELECT("nation");
        sql.SELECT("gender");
        sql.SELECT("birth");
        sql.SELECT("ident_start_date");
        sql.SELECT("ident_end_date");
        sql.SELECT("ident_pic");
        sql.SELECT("backup_phone_1");
        sql.SELECT("backup_phone_2");
        sql.SELECT("emergency_contact_name");
        sql.SELECT("emergency_contact_phone");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_customer");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Customer record = (Customer) parameter.get("record");
        CustomerExample example = (CustomerExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_customer");
        
        if (record.getCustomerId() != null) {
            sql.SET("customer_id = #{record.customerId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerName() != null) {
            sql.SET("customer_name = #{record.customerName,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentityType() != null) {
            sql.SET("identity_type = #{record.identityType,jdbcType=INTEGER}");
        }
        
        if (record.getIdentityNo() != null) {
            sql.SET("identity_no = #{record.identityNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{record.phone,jdbcType=VARCHAR}");
        }
        
        if (record.getAliasName() != null) {
            sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        }
        
        if (record.getWechat() != null) {
            sql.SET("wechat = #{record.wechat,jdbcType=VARCHAR}");
        }
        
        if (record.getLang() != null) {
            sql.SET("lang = #{record.lang,jdbcType=VARCHAR}");
        }
        
        if (record.getNation() != null) {
            sql.SET("nation = #{record.nation,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.SET("gender = #{record.gender,jdbcType=VARCHAR}");
        }
        
        if (record.getBirth() != null) {
            sql.SET("birth = #{record.birth,jdbcType=DATE}");
        }
        
        if (record.getIdentStartDate() != null) {
            sql.SET("ident_start_date = #{record.identStartDate,jdbcType=DATE}");
        }
        
        if (record.getIdentEndDate() != null) {
            sql.SET("ident_end_date = #{record.identEndDate,jdbcType=DATE}");
        }
        
        if (record.getIdentPic() != null) {
            sql.SET("ident_pic = #{record.identPic,jdbcType=VARCHAR}");
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
        sql.UPDATE("t_customer");
        
        sql.SET("customer_id = #{record.customerId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("customer_name = #{record.customerName,jdbcType=VARCHAR}");
        sql.SET("identity_type = #{record.identityType,jdbcType=INTEGER}");
        sql.SET("identity_no = #{record.identityNo,jdbcType=VARCHAR}");
        sql.SET("phone = #{record.phone,jdbcType=VARCHAR}");
        sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        sql.SET("wechat = #{record.wechat,jdbcType=VARCHAR}");
        sql.SET("lang = #{record.lang,jdbcType=VARCHAR}");
        sql.SET("nation = #{record.nation,jdbcType=VARCHAR}");
        sql.SET("gender = #{record.gender,jdbcType=VARCHAR}");
        sql.SET("birth = #{record.birth,jdbcType=DATE}");
        sql.SET("ident_start_date = #{record.identStartDate,jdbcType=DATE}");
        sql.SET("ident_end_date = #{record.identEndDate,jdbcType=DATE}");
        sql.SET("ident_pic = #{record.identPic,jdbcType=VARCHAR}");
        sql.SET("backup_phone_1 = #{record.backupPhone1,jdbcType=VARCHAR}");
        sql.SET("backup_phone_2 = #{record.backupPhone2,jdbcType=VARCHAR}");
        sql.SET("emergency_contact_name = #{record.emergencyContactName,jdbcType=VARCHAR}");
        sql.SET("emergency_contact_phone = #{record.emergencyContactPhone,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        CustomerExample example = (CustomerExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_customer");
        
        sql.SET("customer_id = #{record.customerId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("customer_name = #{record.customerName,jdbcType=VARCHAR}");
        sql.SET("identity_type = #{record.identityType,jdbcType=INTEGER}");
        sql.SET("identity_no = #{record.identityNo,jdbcType=VARCHAR}");
        sql.SET("phone = #{record.phone,jdbcType=VARCHAR}");
        sql.SET("alias_name = #{record.aliasName,jdbcType=VARCHAR}");
        sql.SET("email = #{record.email,jdbcType=VARCHAR}");
        sql.SET("wechat = #{record.wechat,jdbcType=VARCHAR}");
        sql.SET("lang = #{record.lang,jdbcType=VARCHAR}");
        sql.SET("nation = #{record.nation,jdbcType=VARCHAR}");
        sql.SET("gender = #{record.gender,jdbcType=VARCHAR}");
        sql.SET("birth = #{record.birth,jdbcType=DATE}");
        sql.SET("ident_start_date = #{record.identStartDate,jdbcType=DATE}");
        sql.SET("ident_end_date = #{record.identEndDate,jdbcType=DATE}");
        sql.SET("ident_pic = #{record.identPic,jdbcType=VARCHAR}");
        sql.SET("backup_phone_1 = #{record.backupPhone1,jdbcType=VARCHAR}");
        sql.SET("backup_phone_2 = #{record.backupPhone2,jdbcType=VARCHAR}");
        sql.SET("emergency_contact_name = #{record.emergencyContactName,jdbcType=VARCHAR}");
        sql.SET("emergency_contact_phone = #{record.emergencyContactPhone,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        CustomerExample example = (CustomerExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Customer record) {
        SQL sql = new SQL();
        sql.UPDATE("t_customer");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getCustomerName() != null) {
            sql.SET("customer_name = #{customerName,jdbcType=VARCHAR}");
        }
        
        if (record.getIdentityType() != null) {
            sql.SET("identity_type = #{identityType,jdbcType=INTEGER}");
        }
        
        if (record.getIdentityNo() != null) {
            sql.SET("identity_no = #{identityNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPhone() != null) {
            sql.SET("phone = #{phone,jdbcType=VARCHAR}");
        }
        
        if (record.getAliasName() != null) {
            sql.SET("alias_name = #{aliasName,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{email,jdbcType=VARCHAR}");
        }
        
        if (record.getWechat() != null) {
            sql.SET("wechat = #{wechat,jdbcType=VARCHAR}");
        }
        
        if (record.getLang() != null) {
            sql.SET("lang = #{lang,jdbcType=VARCHAR}");
        }
        
        if (record.getNation() != null) {
            sql.SET("nation = #{nation,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.SET("gender = #{gender,jdbcType=VARCHAR}");
        }
        
        if (record.getBirth() != null) {
            sql.SET("birth = #{birth,jdbcType=DATE}");
        }
        
        if (record.getIdentStartDate() != null) {
            sql.SET("ident_start_date = #{identStartDate,jdbcType=DATE}");
        }
        
        if (record.getIdentEndDate() != null) {
            sql.SET("ident_end_date = #{identEndDate,jdbcType=DATE}");
        }
        
        if (record.getIdentPic() != null) {
            sql.SET("ident_pic = #{identPic,jdbcType=VARCHAR}");
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
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getModifyTime() != null) {
            sql.SET("modify_time = #{modifyTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=LONGVARCHAR}");
        }
        
        sql.WHERE("customer_id = #{customerId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, CustomerExample example, boolean includeExamplePhrase) {
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