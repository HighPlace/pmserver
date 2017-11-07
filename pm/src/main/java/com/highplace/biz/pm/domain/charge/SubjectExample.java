package com.highplace.biz.pm.domain.charge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubjectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SubjectExample() {
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

        public Criteria andSubjectIdIsNull() {
            addCriterion("subject_id is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNotNull() {
            addCriterion("subject_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdEqualTo(Long value) {
            addCriterion("subject_id =", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotEqualTo(Long value) {
            addCriterion("subject_id <>", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThan(Long value) {
            addCriterion("subject_id >", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThanOrEqualTo(Long value) {
            addCriterion("subject_id >=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThan(Long value) {
            addCriterion("subject_id <", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThanOrEqualTo(Long value) {
            addCriterion("subject_id <=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIn(List<Long> values) {
            addCriterion("subject_id in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotIn(List<Long> values) {
            addCriterion("subject_id not in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdBetween(Long value1, Long value2) {
            addCriterion("subject_id between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotBetween(Long value1, Long value2) {
            addCriterion("subject_id not between", value1, value2, "subjectId");
            return (Criteria) this;
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

        public Criteria andSubjectNameIsNull() {
            addCriterion("subject_name is null");
            return (Criteria) this;
        }

        public Criteria andSubjectNameIsNotNull() {
            addCriterion("subject_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectNameEqualTo(String value) {
            addCriterion("subject_name =", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotEqualTo(String value) {
            addCriterion("subject_name <>", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameGreaterThan(String value) {
            addCriterion("subject_name >", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("subject_name >=", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLessThan(String value) {
            addCriterion("subject_name <", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLessThanOrEqualTo(String value) {
            addCriterion("subject_name <=", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameLike(String value) {
            addCriterion("subject_name like", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotLike(String value) {
            addCriterion("subject_name not like", value, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameIn(List<String> values) {
            addCriterion("subject_name in", values, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotIn(List<String> values) {
            addCriterion("subject_name not in", values, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameBetween(String value1, String value2) {
            addCriterion("subject_name between", value1, value2, "subjectName");
            return (Criteria) this;
        }

        public Criteria andSubjectNameNotBetween(String value1, String value2) {
            addCriterion("subject_name not between", value1, value2, "subjectName");
            return (Criteria) this;
        }

        public Criteria andChargeCycleIsNull() {
            addCriterion("charge_cycle is null");
            return (Criteria) this;
        }

        public Criteria andChargeCycleIsNotNull() {
            addCriterion("charge_cycle is not null");
            return (Criteria) this;
        }

        public Criteria andChargeCycleEqualTo(Integer value) {
            addCriterion("charge_cycle =", value, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleNotEqualTo(Integer value) {
            addCriterion("charge_cycle <>", value, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleGreaterThan(Integer value) {
            addCriterion("charge_cycle >", value, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_cycle >=", value, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleLessThan(Integer value) {
            addCriterion("charge_cycle <", value, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleLessThanOrEqualTo(Integer value) {
            addCriterion("charge_cycle <=", value, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleIn(List<Integer> values) {
            addCriterion("charge_cycle in", values, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleNotIn(List<Integer> values) {
            addCriterion("charge_cycle not in", values, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleBetween(Integer value1, Integer value2) {
            addCriterion("charge_cycle between", value1, value2, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andChargeCycleNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_cycle not between", value1, value2, "chargeCycle");
            return (Criteria) this;
        }

        public Criteria andCycleFlagIsNull() {
            addCriterion("cycle_flag is null");
            return (Criteria) this;
        }

        public Criteria andCycleFlagIsNotNull() {
            addCriterion("cycle_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCycleFlagEqualTo(Integer value) {
            addCriterion("cycle_flag =", value, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagNotEqualTo(Integer value) {
            addCriterion("cycle_flag <>", value, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagGreaterThan(Integer value) {
            addCriterion("cycle_flag >", value, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("cycle_flag >=", value, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagLessThan(Integer value) {
            addCriterion("cycle_flag <", value, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagLessThanOrEqualTo(Integer value) {
            addCriterion("cycle_flag <=", value, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagIn(List<Integer> values) {
            addCriterion("cycle_flag in", values, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagNotIn(List<Integer> values) {
            addCriterion("cycle_flag not in", values, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagBetween(Integer value1, Integer value2) {
            addCriterion("cycle_flag between", value1, value2, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andCycleFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("cycle_flag not between", value1, value2, "cycleFlag");
            return (Criteria) this;
        }

        public Criteria andLateFeeIsNull() {
            addCriterion("late_fee is null");
            return (Criteria) this;
        }

        public Criteria andLateFeeIsNotNull() {
            addCriterion("late_fee is not null");
            return (Criteria) this;
        }

        public Criteria andLateFeeEqualTo(Double value) {
            addCriterion("late_fee =", value, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeNotEqualTo(Double value) {
            addCriterion("late_fee <>", value, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeGreaterThan(Double value) {
            addCriterion("late_fee >", value, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("late_fee >=", value, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeLessThan(Double value) {
            addCriterion("late_fee <", value, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeLessThanOrEqualTo(Double value) {
            addCriterion("late_fee <=", value, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeIn(List<Double> values) {
            addCriterion("late_fee in", values, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeNotIn(List<Double> values) {
            addCriterion("late_fee not in", values, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeBetween(Double value1, Double value2) {
            addCriterion("late_fee between", value1, value2, "lateFee");
            return (Criteria) this;
        }

        public Criteria andLateFeeNotBetween(Double value1, Double value2) {
            addCriterion("late_fee not between", value1, value2, "lateFee");
            return (Criteria) this;
        }

        public Criteria andRateIsNull() {
            addCriterion("rate is null");
            return (Criteria) this;
        }

        public Criteria andRateIsNotNull() {
            addCriterion("rate is not null");
            return (Criteria) this;
        }

        public Criteria andRateEqualTo(Double value) {
            addCriterion("rate =", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotEqualTo(Double value) {
            addCriterion("rate <>", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThan(Double value) {
            addCriterion("rate >", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateGreaterThanOrEqualTo(Double value) {
            addCriterion("rate >=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThan(Double value) {
            addCriterion("rate <", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateLessThanOrEqualTo(Double value) {
            addCriterion("rate <=", value, "rate");
            return (Criteria) this;
        }

        public Criteria andRateIn(List<Double> values) {
            addCriterion("rate in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotIn(List<Double> values) {
            addCriterion("rate not in", values, "rate");
            return (Criteria) this;
        }

        public Criteria andRateBetween(Double value1, Double value2) {
            addCriterion("rate between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andRateNotBetween(Double value1, Double value2) {
            addCriterion("rate not between", value1, value2, "rate");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneIsNull() {
            addCriterion("fee_level_one is null");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneIsNotNull() {
            addCriterion("fee_level_one is not null");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneEqualTo(Double value) {
            addCriterion("fee_level_one =", value, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneNotEqualTo(Double value) {
            addCriterion("fee_level_one <>", value, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneGreaterThan(Double value) {
            addCriterion("fee_level_one >", value, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneGreaterThanOrEqualTo(Double value) {
            addCriterion("fee_level_one >=", value, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneLessThan(Double value) {
            addCriterion("fee_level_one <", value, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneLessThanOrEqualTo(Double value) {
            addCriterion("fee_level_one <=", value, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneIn(List<Double> values) {
            addCriterion("fee_level_one in", values, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneNotIn(List<Double> values) {
            addCriterion("fee_level_one not in", values, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneBetween(Double value1, Double value2) {
            addCriterion("fee_level_one between", value1, value2, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andFeeLevelOneNotBetween(Double value1, Double value2) {
            addCriterion("fee_level_one not between", value1, value2, "feeLevelOne");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitIsNull() {
            addCriterion("level_one_toplimit is null");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitIsNotNull() {
            addCriterion("level_one_toplimit is not null");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitEqualTo(Double value) {
            addCriterion("level_one_toplimit =", value, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitNotEqualTo(Double value) {
            addCriterion("level_one_toplimit <>", value, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitGreaterThan(Double value) {
            addCriterion("level_one_toplimit >", value, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitGreaterThanOrEqualTo(Double value) {
            addCriterion("level_one_toplimit >=", value, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitLessThan(Double value) {
            addCriterion("level_one_toplimit <", value, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitLessThanOrEqualTo(Double value) {
            addCriterion("level_one_toplimit <=", value, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitIn(List<Double> values) {
            addCriterion("level_one_toplimit in", values, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitNotIn(List<Double> values) {
            addCriterion("level_one_toplimit not in", values, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitBetween(Double value1, Double value2) {
            addCriterion("level_one_toplimit between", value1, value2, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelOneToplimitNotBetween(Double value1, Double value2) {
            addCriterion("level_one_toplimit not between", value1, value2, "levelOneToplimit");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoIsNull() {
            addCriterion("fee_level_two is null");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoIsNotNull() {
            addCriterion("fee_level_two is not null");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoEqualTo(Double value) {
            addCriterion("fee_level_two =", value, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoNotEqualTo(Double value) {
            addCriterion("fee_level_two <>", value, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoGreaterThan(Double value) {
            addCriterion("fee_level_two >", value, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoGreaterThanOrEqualTo(Double value) {
            addCriterion("fee_level_two >=", value, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoLessThan(Double value) {
            addCriterion("fee_level_two <", value, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoLessThanOrEqualTo(Double value) {
            addCriterion("fee_level_two <=", value, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoIn(List<Double> values) {
            addCriterion("fee_level_two in", values, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoNotIn(List<Double> values) {
            addCriterion("fee_level_two not in", values, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoBetween(Double value1, Double value2) {
            addCriterion("fee_level_two between", value1, value2, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andFeeLevelTwoNotBetween(Double value1, Double value2) {
            addCriterion("fee_level_two not between", value1, value2, "feeLevelTwo");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitIsNull() {
            addCriterion("level_two_toplimit is null");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitIsNotNull() {
            addCriterion("level_two_toplimit is not null");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitEqualTo(Double value) {
            addCriterion("level_two_toplimit =", value, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitNotEqualTo(Double value) {
            addCriterion("level_two_toplimit <>", value, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitGreaterThan(Double value) {
            addCriterion("level_two_toplimit >", value, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitGreaterThanOrEqualTo(Double value) {
            addCriterion("level_two_toplimit >=", value, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitLessThan(Double value) {
            addCriterion("level_two_toplimit <", value, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitLessThanOrEqualTo(Double value) {
            addCriterion("level_two_toplimit <=", value, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitIn(List<Double> values) {
            addCriterion("level_two_toplimit in", values, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitNotIn(List<Double> values) {
            addCriterion("level_two_toplimit not in", values, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitBetween(Double value1, Double value2) {
            addCriterion("level_two_toplimit between", value1, value2, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelTwoToplimitNotBetween(Double value1, Double value2) {
            addCriterion("level_two_toplimit not between", value1, value2, "levelTwoToplimit");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeIsNull() {
            addCriterion("fee_level_three is null");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeIsNotNull() {
            addCriterion("fee_level_three is not null");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeEqualTo(Double value) {
            addCriterion("fee_level_three =", value, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeNotEqualTo(Double value) {
            addCriterion("fee_level_three <>", value, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeGreaterThan(Double value) {
            addCriterion("fee_level_three >", value, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeGreaterThanOrEqualTo(Double value) {
            addCriterion("fee_level_three >=", value, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeLessThan(Double value) {
            addCriterion("fee_level_three <", value, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeLessThanOrEqualTo(Double value) {
            addCriterion("fee_level_three <=", value, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeIn(List<Double> values) {
            addCriterion("fee_level_three in", values, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeNotIn(List<Double> values) {
            addCriterion("fee_level_three not in", values, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeBetween(Double value1, Double value2) {
            addCriterion("fee_level_three between", value1, value2, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andFeeLevelThreeNotBetween(Double value1, Double value2) {
            addCriterion("fee_level_three not between", value1, value2, "feeLevelThree");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitIsNull() {
            addCriterion("level_three_toplimit is null");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitIsNotNull() {
            addCriterion("level_three_toplimit is not null");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitEqualTo(Double value) {
            addCriterion("level_three_toplimit =", value, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitNotEqualTo(Double value) {
            addCriterion("level_three_toplimit <>", value, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitGreaterThan(Double value) {
            addCriterion("level_three_toplimit >", value, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitGreaterThanOrEqualTo(Double value) {
            addCriterion("level_three_toplimit >=", value, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitLessThan(Double value) {
            addCriterion("level_three_toplimit <", value, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitLessThanOrEqualTo(Double value) {
            addCriterion("level_three_toplimit <=", value, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitIn(List<Double> values) {
            addCriterion("level_three_toplimit in", values, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitNotIn(List<Double> values) {
            addCriterion("level_three_toplimit not in", values, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitBetween(Double value1, Double value2) {
            addCriterion("level_three_toplimit between", value1, value2, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andLevelThreeToplimitNotBetween(Double value1, Double value2) {
            addCriterion("level_three_toplimit not between", value1, value2, "levelThreeToplimit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitIsNull() {
            addCriterion("fee_data_unit is null");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitIsNotNull() {
            addCriterion("fee_data_unit is not null");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitEqualTo(String value) {
            addCriterion("fee_data_unit =", value, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitNotEqualTo(String value) {
            addCriterion("fee_data_unit <>", value, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitGreaterThan(String value) {
            addCriterion("fee_data_unit >", value, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitGreaterThanOrEqualTo(String value) {
            addCriterion("fee_data_unit >=", value, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitLessThan(String value) {
            addCriterion("fee_data_unit <", value, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitLessThanOrEqualTo(String value) {
            addCriterion("fee_data_unit <=", value, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitLike(String value) {
            addCriterion("fee_data_unit like", value, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitNotLike(String value) {
            addCriterion("fee_data_unit not like", value, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitIn(List<String> values) {
            addCriterion("fee_data_unit in", values, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitNotIn(List<String> values) {
            addCriterion("fee_data_unit not in", values, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitBetween(String value1, String value2) {
            addCriterion("fee_data_unit between", value1, value2, "feeDataUnit");
            return (Criteria) this;
        }

        public Criteria andFeeDataUnitNotBetween(String value1, String value2) {
            addCriterion("fee_data_unit not between", value1, value2, "feeDataUnit");
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