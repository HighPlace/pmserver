package com.highplace.biz.pm.dao.base;

import com.highplace.biz.pm.domain.base.Car;
import com.highplace.biz.pm.domain.base.CarExample.Criteria;
import com.highplace.biz.pm.domain.base.CarExample.Criterion;
import com.highplace.biz.pm.domain.base.CarExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class CarSqlProvider {

    public String countByExample(CarExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_car");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(CarExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_car");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Car record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_car");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationId() != null) {
            sql.VALUES("relation_id", "#{relationId,jdbcType=BIGINT}");
        }
        
        if (record.getPlateNo() != null) {
            sql.VALUES("plate_no", "#{plateNo,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }
        
        if (record.getChargeStatus() != null) {
            sql.VALUES("charge_status", "#{chargeStatus,jdbcType=INTEGER}");
        }
        
        if (record.getParkInfo() != null) {
            sql.VALUES("park_info", "#{parkInfo,jdbcType=VARCHAR}");
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

    public String selectByExampleWithBLOBs(CarExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("car_id");
        } else {
            sql.SELECT("car_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("relation_id");
        sql.SELECT("plate_no");
        sql.SELECT("type");
        sql.SELECT("charge_status");
        sql.SELECT("park_info");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("remark");
        sql.FROM("t_car");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(CarExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("car_id");
        } else {
            sql.SELECT("car_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("relation_id");
        sql.SELECT("plate_no");
        sql.SELECT("type");
        sql.SELECT("charge_status");
        sql.SELECT("park_info");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_car");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Car record = (Car) parameter.get("record");
        CarExample example = (CarExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_car");
        
        if (record.getCarId() != null) {
            sql.SET("car_id = #{record.carId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationId() != null) {
            sql.SET("relation_id = #{record.relationId,jdbcType=BIGINT}");
        }
        
        if (record.getPlateNo() != null) {
            sql.SET("plate_no = #{record.plateNo,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{record.type,jdbcType=INTEGER}");
        }
        
        if (record.getChargeStatus() != null) {
            sql.SET("charge_status = #{record.chargeStatus,jdbcType=INTEGER}");
        }
        
        if (record.getParkInfo() != null) {
            sql.SET("park_info = #{record.parkInfo,jdbcType=VARCHAR}");
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
        sql.UPDATE("t_car");
        
        sql.SET("car_id = #{record.carId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("relation_id = #{record.relationId,jdbcType=BIGINT}");
        sql.SET("plate_no = #{record.plateNo,jdbcType=VARCHAR}");
        sql.SET("type = #{record.type,jdbcType=INTEGER}");
        sql.SET("charge_status = #{record.chargeStatus,jdbcType=INTEGER}");
        sql.SET("park_info = #{record.parkInfo,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        CarExample example = (CarExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_car");
        
        sql.SET("car_id = #{record.carId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("relation_id = #{record.relationId,jdbcType=BIGINT}");
        sql.SET("plate_no = #{record.plateNo,jdbcType=VARCHAR}");
        sql.SET("type = #{record.type,jdbcType=INTEGER}");
        sql.SET("charge_status = #{record.chargeStatus,jdbcType=INTEGER}");
        sql.SET("park_info = #{record.parkInfo,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        CarExample example = (CarExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Car record) {
        SQL sql = new SQL();
        sql.UPDATE("t_car");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getRelationId() != null) {
            sql.SET("relation_id = #{relationId,jdbcType=BIGINT}");
        }
        
        if (record.getPlateNo() != null) {
            sql.SET("plate_no = #{plateNo,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }
        
        if (record.getChargeStatus() != null) {
            sql.SET("charge_status = #{chargeStatus,jdbcType=INTEGER}");
        }
        
        if (record.getParkInfo() != null) {
            sql.SET("park_info = #{parkInfo,jdbcType=VARCHAR}");
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
        
        sql.WHERE("car_id = #{carId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, CarExample example, boolean includeExamplePhrase) {
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