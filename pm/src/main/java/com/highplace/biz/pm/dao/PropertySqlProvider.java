package com.highplace.biz.pm.dao;

import com.highplace.biz.pm.domain.Property;
import com.highplace.biz.pm.domain.PropertyExample.Criteria;
import com.highplace.biz.pm.domain.PropertyExample.Criterion;
import com.highplace.biz.pm.domain.PropertyExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class PropertySqlProvider {

    public String countByExample(PropertyExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_property");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(PropertyExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_property");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Property record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_property");
        
        if (record.getProductInstId() != null) {
            sql.VALUES("product_inst_id", "#{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyType() != null) {
            sql.VALUES("property_type", "#{propertyType,jdbcType=INTEGER}");
        }
        
        if (record.getZoneId() != null) {
            sql.VALUES("zone_id", "#{zoneId,jdbcType=VARCHAR}");
        }
        
        if (record.getBuildingId() != null) {
            sql.VALUES("building_id", "#{buildingId,jdbcType=VARCHAR}");
        }
        
        if (record.getUnitId() != null) {
            sql.VALUES("unit_id", "#{unitId,jdbcType=VARCHAR}");
        }
        
        if (record.getRoomId() != null) {
            sql.VALUES("room_id", "#{roomId,jdbcType=VARCHAR}");
        }
        
        if (record.getAreaUnit() != null) {
            sql.VALUES("area_unit", "#{areaUnit,jdbcType=INTEGER}");
        }
        
        if (record.getPropertyArea() != null) {
            sql.VALUES("property_area", "#{propertyArea,jdbcType=DOUBLE}");
        }
        
        if (record.getFloorArea() != null) {
            sql.VALUES("floor_area", "#{floorArea,jdbcType=DOUBLE}");
        }
        
        if (record.getHouseType() != null) {
            sql.VALUES("house_type", "#{houseType,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
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

    public String selectByExampleWithBLOBs(PropertyExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("property_id");
        } else {
            sql.SELECT("property_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("property_type");
        sql.SELECT("zone_id");
        sql.SELECT("building_id");
        sql.SELECT("unit_id");
        sql.SELECT("room_id");
        sql.SELECT("area_unit");
        sql.SELECT("property_area");
        sql.SELECT("floor_area");
        sql.SELECT("house_type");
        sql.SELECT("status");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.SELECT("remark");
        sql.FROM("t_property");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String selectByExample(PropertyExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("property_id");
        } else {
            sql.SELECT("property_id");
        }
        sql.SELECT("product_inst_id");
        sql.SELECT("property_type");
        sql.SELECT("zone_id");
        sql.SELECT("building_id");
        sql.SELECT("unit_id");
        sql.SELECT("room_id");
        sql.SELECT("area_unit");
        sql.SELECT("property_area");
        sql.SELECT("floor_area");
        sql.SELECT("house_type");
        sql.SELECT("status");
        sql.SELECT("create_time");
        sql.SELECT("modify_time");
        sql.FROM("t_property");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Property record = (Property) parameter.get("record");
        PropertyExample example = (PropertyExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_property");
        
        if (record.getPropertyId() != null) {
            sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        }
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyType() != null) {
            sql.SET("property_type = #{record.propertyType,jdbcType=INTEGER}");
        }
        
        if (record.getZoneId() != null) {
            sql.SET("zone_id = #{record.zoneId,jdbcType=VARCHAR}");
        }
        
        if (record.getBuildingId() != null) {
            sql.SET("building_id = #{record.buildingId,jdbcType=VARCHAR}");
        }
        
        if (record.getUnitId() != null) {
            sql.SET("unit_id = #{record.unitId,jdbcType=VARCHAR}");
        }
        
        if (record.getRoomId() != null) {
            sql.SET("room_id = #{record.roomId,jdbcType=VARCHAR}");
        }
        
        if (record.getAreaUnit() != null) {
            sql.SET("area_unit = #{record.areaUnit,jdbcType=INTEGER}");
        }
        
        if (record.getPropertyArea() != null) {
            sql.SET("property_area = #{record.propertyArea,jdbcType=DOUBLE}");
        }
        
        if (record.getFloorArea() != null) {
            sql.SET("floor_area = #{record.floorArea,jdbcType=DOUBLE}");
        }
        
        if (record.getHouseType() != null) {
            sql.SET("house_type = #{record.houseType,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=INTEGER}");
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
        sql.UPDATE("t_property");
        
        sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("property_type = #{record.propertyType,jdbcType=INTEGER}");
        sql.SET("zone_id = #{record.zoneId,jdbcType=VARCHAR}");
        sql.SET("building_id = #{record.buildingId,jdbcType=VARCHAR}");
        sql.SET("unit_id = #{record.unitId,jdbcType=VARCHAR}");
        sql.SET("room_id = #{record.roomId,jdbcType=VARCHAR}");
        sql.SET("area_unit = #{record.areaUnit,jdbcType=INTEGER}");
        sql.SET("property_area = #{record.propertyArea,jdbcType=DOUBLE}");
        sql.SET("floor_area = #{record.floorArea,jdbcType=DOUBLE}");
        sql.SET("house_type = #{record.houseType,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        sql.SET("remark = #{record.remark,jdbcType=LONGVARCHAR}");
        
        PropertyExample example = (PropertyExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_property");
        
        sql.SET("property_id = #{record.propertyId,jdbcType=BIGINT}");
        sql.SET("product_inst_id = #{record.productInstId,jdbcType=VARCHAR}");
        sql.SET("property_type = #{record.propertyType,jdbcType=INTEGER}");
        sql.SET("zone_id = #{record.zoneId,jdbcType=VARCHAR}");
        sql.SET("building_id = #{record.buildingId,jdbcType=VARCHAR}");
        sql.SET("unit_id = #{record.unitId,jdbcType=VARCHAR}");
        sql.SET("room_id = #{record.roomId,jdbcType=VARCHAR}");
        sql.SET("area_unit = #{record.areaUnit,jdbcType=INTEGER}");
        sql.SET("property_area = #{record.propertyArea,jdbcType=DOUBLE}");
        sql.SET("floor_area = #{record.floorArea,jdbcType=DOUBLE}");
        sql.SET("house_type = #{record.houseType,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        sql.SET("modify_time = #{record.modifyTime,jdbcType=TIMESTAMP}");
        
        PropertyExample example = (PropertyExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Property record) {
        SQL sql = new SQL();
        sql.UPDATE("t_property");
        
        if (record.getProductInstId() != null) {
            sql.SET("product_inst_id = #{productInstId,jdbcType=VARCHAR}");
        }
        
        if (record.getPropertyType() != null) {
            sql.SET("property_type = #{propertyType,jdbcType=INTEGER}");
        }
        
        if (record.getZoneId() != null) {
            sql.SET("zone_id = #{zoneId,jdbcType=VARCHAR}");
        }
        
        if (record.getBuildingId() != null) {
            sql.SET("building_id = #{buildingId,jdbcType=VARCHAR}");
        }
        
        if (record.getUnitId() != null) {
            sql.SET("unit_id = #{unitId,jdbcType=VARCHAR}");
        }
        
        if (record.getRoomId() != null) {
            sql.SET("room_id = #{roomId,jdbcType=VARCHAR}");
        }
        
        if (record.getAreaUnit() != null) {
            sql.SET("area_unit = #{areaUnit,jdbcType=INTEGER}");
        }
        
        if (record.getPropertyArea() != null) {
            sql.SET("property_area = #{propertyArea,jdbcType=DOUBLE}");
        }
        
        if (record.getFloorArea() != null) {
            sql.SET("floor_area = #{floorArea,jdbcType=DOUBLE}");
        }
        
        if (record.getHouseType() != null) {
            sql.SET("house_type = #{houseType,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
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
        
        sql.WHERE("property_id = #{propertyId,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, PropertyExample example, boolean includeExamplePhrase) {
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