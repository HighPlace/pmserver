package com.highplace.biz.pm.domain.charge;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ChargeWaterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChargeWaterExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andProductInstIdIsNull() {
            addCriterion("product_inst_id is null");
            return (Criteria) this;
        }

        public Criteria andProductInstIdIsNotNull() {
            addCriterion("product_inst_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductInstIdEqualTo(String value) {
            addCriterion("product_inst_id =", value, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdNotEqualTo(String value) {
            addCriterion("product_inst_id <>", value, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdGreaterThan(String value) {
            addCriterion("product_inst_id >", value, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_inst_id >=", value, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdLessThan(String value) {
            addCriterion("product_inst_id <", value, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdLessThanOrEqualTo(String value) {
            addCriterion("product_inst_id <=", value, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdLike(String value) {
            addCriterion("product_inst_id like", value, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdNotLike(String value) {
            addCriterion("product_inst_id not like", value, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdIn(List<String> values) {
            addCriterion("product_inst_id in", values, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdNotIn(List<String> values) {
            addCriterion("product_inst_id not in", values, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdBetween(String value1, String value2) {
            addCriterion("product_inst_id between", value1, value2, "productInstId");
            return (Criteria) this;
        }

        public Criteria andProductInstIdNotBetween(String value1, String value2) {
            addCriterion("product_inst_id not between", value1, value2, "productInstId");
            return (Criteria) this;
        }

        public Criteria andChargeIdIsNull() {
            addCriterion("charge_id is null");
            return (Criteria) this;
        }

        public Criteria andChargeIdIsNotNull() {
            addCriterion("charge_id is not null");
            return (Criteria) this;
        }

        public Criteria andChargeIdEqualTo(Long value) {
            addCriterion("charge_id =", value, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdNotEqualTo(Long value) {
            addCriterion("charge_id <>", value, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdGreaterThan(Long value) {
            addCriterion("charge_id >", value, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("charge_id >=", value, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdLessThan(Long value) {
            addCriterion("charge_id <", value, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdLessThanOrEqualTo(Long value) {
            addCriterion("charge_id <=", value, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdIn(List<Long> values) {
            addCriterion("charge_id in", values, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdNotIn(List<Long> values) {
            addCriterion("charge_id not in", values, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdBetween(Long value1, Long value2) {
            addCriterion("charge_id between", value1, value2, "chargeId");
            return (Criteria) this;
        }

        public Criteria andChargeIdNotBetween(Long value1, Long value2) {
            addCriterion("charge_id not between", value1, value2, "chargeId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNull() {
            addCriterion("property_id is null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIsNotNull() {
            addCriterion("property_id is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyIdEqualTo(Long value) {
            addCriterion("property_id =", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotEqualTo(Long value) {
            addCriterion("property_id <>", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThan(Long value) {
            addCriterion("property_id >", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("property_id >=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThan(Long value) {
            addCriterion("property_id <", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThanOrEqualTo(Long value) {
            addCriterion("property_id <=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIn(List<Long> values) {
            addCriterion("property_id in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotIn(List<Long> values) {
            addCriterion("property_id not in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdBetween(Long value1, Long value2) {
            addCriterion("property_id between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotBetween(Long value1, Long value2) {
            addCriterion("property_id not between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeIsNull() {
            addCriterion("fee_data_type is null");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeIsNotNull() {
            addCriterion("fee_data_type is not null");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeEqualTo(Integer value) {
            addCriterion("fee_data_type =", value, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeNotEqualTo(Integer value) {
            addCriterion("fee_data_type <>", value, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeGreaterThan(Integer value) {
            addCriterion("fee_data_type >", value, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("fee_data_type >=", value, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeLessThan(Integer value) {
            addCriterion("fee_data_type <", value, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeLessThanOrEqualTo(Integer value) {
            addCriterion("fee_data_type <=", value, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeIn(List<Integer> values) {
            addCriterion("fee_data_type in", values, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeNotIn(List<Integer> values) {
            addCriterion("fee_data_type not in", values, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeBetween(Integer value1, Integer value2) {
            addCriterion("fee_data_type between", value1, value2, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andFeeDataTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("fee_data_type not between", value1, value2, "feeDataType");
            return (Criteria) this;
        }

        public Criteria andPropertyNameIsNull() {
            addCriterion("property_name is null");
            return (Criteria) this;
        }

        public Criteria andPropertyNameIsNotNull() {
            addCriterion("property_name is not null");
            return (Criteria) this;
        }

        public Criteria andPropertyNameEqualTo(String value) {
            addCriterion("property_name =", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameNotEqualTo(String value) {
            addCriterion("property_name <>", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameGreaterThan(String value) {
            addCriterion("property_name >", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameGreaterThanOrEqualTo(String value) {
            addCriterion("property_name >=", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameLessThan(String value) {
            addCriterion("property_name <", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameLessThanOrEqualTo(String value) {
            addCriterion("property_name <=", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameLike(String value) {
            addCriterion("property_name like", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameNotLike(String value) {
            addCriterion("property_name not like", value, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameIn(List<String> values) {
            addCriterion("property_name in", values, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameNotIn(List<String> values) {
            addCriterion("property_name not in", values, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameBetween(String value1, String value2) {
            addCriterion("property_name between", value1, value2, "propertyName");
            return (Criteria) this;
        }

        public Criteria andPropertyNameNotBetween(String value1, String value2) {
            addCriterion("property_name not between", value1, value2, "propertyName");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNull() {
            addCriterion("begin_date is null");
            return (Criteria) this;
        }

        public Criteria andBeginDateIsNotNull() {
            addCriterion("begin_date is not null");
            return (Criteria) this;
        }

        public Criteria andBeginDateEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date =", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date <>", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThan(Date value) {
            addCriterionForJDBCDate("begin_date >", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date >=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThan(Date value) {
            addCriterionForJDBCDate("begin_date <", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin_date <=", value, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateIn(List<Date> values) {
            addCriterionForJDBCDate("begin_date in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("begin_date not in", values, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin_date between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andBeginDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin_date not between", value1, value2, "beginDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andBeginUsageIsNull() {
            addCriterion("begin_usage is null");
            return (Criteria) this;
        }

        public Criteria andBeginUsageIsNotNull() {
            addCriterion("begin_usage is not null");
            return (Criteria) this;
        }

        public Criteria andBeginUsageEqualTo(Double value) {
            addCriterion("begin_usage =", value, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageNotEqualTo(Double value) {
            addCriterion("begin_usage <>", value, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageGreaterThan(Double value) {
            addCriterion("begin_usage >", value, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageGreaterThanOrEqualTo(Double value) {
            addCriterion("begin_usage >=", value, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageLessThan(Double value) {
            addCriterion("begin_usage <", value, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageLessThanOrEqualTo(Double value) {
            addCriterion("begin_usage <=", value, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageIn(List<Double> values) {
            addCriterion("begin_usage in", values, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageNotIn(List<Double> values) {
            addCriterion("begin_usage not in", values, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageBetween(Double value1, Double value2) {
            addCriterion("begin_usage between", value1, value2, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andBeginUsageNotBetween(Double value1, Double value2) {
            addCriterion("begin_usage not between", value1, value2, "beginUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageIsNull() {
            addCriterion("end_usage is null");
            return (Criteria) this;
        }

        public Criteria andEndUsageIsNotNull() {
            addCriterion("end_usage is not null");
            return (Criteria) this;
        }

        public Criteria andEndUsageEqualTo(Double value) {
            addCriterion("end_usage =", value, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageNotEqualTo(Double value) {
            addCriterion("end_usage <>", value, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageGreaterThan(Double value) {
            addCriterion("end_usage >", value, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageGreaterThanOrEqualTo(Double value) {
            addCriterion("end_usage >=", value, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageLessThan(Double value) {
            addCriterion("end_usage <", value, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageLessThanOrEqualTo(Double value) {
            addCriterion("end_usage <=", value, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageIn(List<Double> values) {
            addCriterion("end_usage in", values, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageNotIn(List<Double> values) {
            addCriterion("end_usage not in", values, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageBetween(Double value1, Double value2) {
            addCriterion("end_usage between", value1, value2, "endUsage");
            return (Criteria) this;
        }

        public Criteria andEndUsageNotBetween(Double value1, Double value2) {
            addCriterion("end_usage not between", value1, value2, "endUsage");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}