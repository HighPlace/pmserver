package com.highplace.biz.pm.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RequestExample() {
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

        public Criteria andRequestIdIsNull() {
            addCriterion("request_id is null");
            return (Criteria) this;
        }

        public Criteria andRequestIdIsNotNull() {
            addCriterion("request_id is not null");
            return (Criteria) this;
        }

        public Criteria andRequestIdEqualTo(Long value) {
            addCriterion("request_id =", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdNotEqualTo(Long value) {
            addCriterion("request_id <>", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdGreaterThan(Long value) {
            addCriterion("request_id >", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdGreaterThanOrEqualTo(Long value) {
            addCriterion("request_id >=", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdLessThan(Long value) {
            addCriterion("request_id <", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdLessThanOrEqualTo(Long value) {
            addCriterion("request_id <=", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdIn(List<Long> values) {
            addCriterion("request_id in", values, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdNotIn(List<Long> values) {
            addCriterion("request_id not in", values, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdBetween(Long value1, Long value2) {
            addCriterion("request_id between", value1, value2, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdNotBetween(Long value1, Long value2) {
            addCriterion("request_id not between", value1, value2, "requestId");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSubTypeIsNull() {
            addCriterion("sub_type is null");
            return (Criteria) this;
        }

        public Criteria andSubTypeIsNotNull() {
            addCriterion("sub_type is not null");
            return (Criteria) this;
        }

        public Criteria andSubTypeEqualTo(String value) {
            addCriterion("sub_type =", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeNotEqualTo(String value) {
            addCriterion("sub_type <>", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeGreaterThan(String value) {
            addCriterion("sub_type >", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeGreaterThanOrEqualTo(String value) {
            addCriterion("sub_type >=", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeLessThan(String value) {
            addCriterion("sub_type <", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeLessThanOrEqualTo(String value) {
            addCriterion("sub_type <=", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeLike(String value) {
            addCriterion("sub_type like", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeNotLike(String value) {
            addCriterion("sub_type not like", value, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeIn(List<String> values) {
            addCriterion("sub_type in", values, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeNotIn(List<String> values) {
            addCriterion("sub_type not in", values, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeBetween(String value1, String value2) {
            addCriterion("sub_type between", value1, value2, "subType");
            return (Criteria) this;
        }

        public Criteria andSubTypeNotBetween(String value1, String value2) {
            addCriterion("sub_type not between", value1, value2, "subType");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(Integer value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(Integer value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(Integer value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(Integer value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(Integer value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<Integer> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<Integer> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(Integer value1, Integer value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAttachment1IsNull() {
            addCriterion("attachment1 is null");
            return (Criteria) this;
        }

        public Criteria andAttachment1IsNotNull() {
            addCriterion("attachment1 is not null");
            return (Criteria) this;
        }

        public Criteria andAttachment1EqualTo(String value) {
            addCriterion("attachment1 =", value, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1NotEqualTo(String value) {
            addCriterion("attachment1 <>", value, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1GreaterThan(String value) {
            addCriterion("attachment1 >", value, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1GreaterThanOrEqualTo(String value) {
            addCriterion("attachment1 >=", value, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1LessThan(String value) {
            addCriterion("attachment1 <", value, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1LessThanOrEqualTo(String value) {
            addCriterion("attachment1 <=", value, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1Like(String value) {
            addCriterion("attachment1 like", value, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1NotLike(String value) {
            addCriterion("attachment1 not like", value, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1In(List<String> values) {
            addCriterion("attachment1 in", values, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1NotIn(List<String> values) {
            addCriterion("attachment1 not in", values, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1Between(String value1, String value2) {
            addCriterion("attachment1 between", value1, value2, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment1NotBetween(String value1, String value2) {
            addCriterion("attachment1 not between", value1, value2, "attachment1");
            return (Criteria) this;
        }

        public Criteria andAttachment2IsNull() {
            addCriterion("attachment2 is null");
            return (Criteria) this;
        }

        public Criteria andAttachment2IsNotNull() {
            addCriterion("attachment2 is not null");
            return (Criteria) this;
        }

        public Criteria andAttachment2EqualTo(String value) {
            addCriterion("attachment2 =", value, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2NotEqualTo(String value) {
            addCriterion("attachment2 <>", value, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2GreaterThan(String value) {
            addCriterion("attachment2 >", value, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2GreaterThanOrEqualTo(String value) {
            addCriterion("attachment2 >=", value, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2LessThan(String value) {
            addCriterion("attachment2 <", value, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2LessThanOrEqualTo(String value) {
            addCriterion("attachment2 <=", value, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2Like(String value) {
            addCriterion("attachment2 like", value, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2NotLike(String value) {
            addCriterion("attachment2 not like", value, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2In(List<String> values) {
            addCriterion("attachment2 in", values, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2NotIn(List<String> values) {
            addCriterion("attachment2 not in", values, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2Between(String value1, String value2) {
            addCriterion("attachment2 between", value1, value2, "attachment2");
            return (Criteria) this;
        }

        public Criteria andAttachment2NotBetween(String value1, String value2) {
            addCriterion("attachment2 not between", value1, value2, "attachment2");
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

        public Criteria andPropertyIdEqualTo(String value) {
            addCriterion("property_id =", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotEqualTo(String value) {
            addCriterion("property_id <>", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThan(String value) {
            addCriterion("property_id >", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdGreaterThanOrEqualTo(String value) {
            addCriterion("property_id >=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThan(String value) {
            addCriterion("property_id <", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLessThanOrEqualTo(String value) {
            addCriterion("property_id <=", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdLike(String value) {
            addCriterion("property_id like", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotLike(String value) {
            addCriterion("property_id not like", value, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdIn(List<String> values) {
            addCriterion("property_id in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotIn(List<String> values) {
            addCriterion("property_id not in", values, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdBetween(String value1, String value2) {
            addCriterion("property_id between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andPropertyIdNotBetween(String value1, String value2) {
            addCriterion("property_id not between", value1, value2, "propertyId");
            return (Criteria) this;
        }

        public Criteria andSubmitterIsNull() {
            addCriterion("submitter is null");
            return (Criteria) this;
        }

        public Criteria andSubmitterIsNotNull() {
            addCriterion("submitter is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitterEqualTo(String value) {
            addCriterion("submitter =", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotEqualTo(String value) {
            addCriterion("submitter <>", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterGreaterThan(String value) {
            addCriterion("submitter >", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterGreaterThanOrEqualTo(String value) {
            addCriterion("submitter >=", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLessThan(String value) {
            addCriterion("submitter <", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLessThanOrEqualTo(String value) {
            addCriterion("submitter <=", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterLike(String value) {
            addCriterion("submitter like", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotLike(String value) {
            addCriterion("submitter not like", value, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterIn(List<String> values) {
            addCriterion("submitter in", values, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotIn(List<String> values) {
            addCriterion("submitter not in", values, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterBetween(String value1, String value2) {
            addCriterion("submitter between", value1, value2, "submitter");
            return (Criteria) this;
        }

        public Criteria andSubmitterNotBetween(String value1, String value2) {
            addCriterion("submitter not between", value1, value2, "submitter");
            return (Criteria) this;
        }

        public Criteria andContactInfoIsNull() {
            addCriterion("contact_info is null");
            return (Criteria) this;
        }

        public Criteria andContactInfoIsNotNull() {
            addCriterion("contact_info is not null");
            return (Criteria) this;
        }

        public Criteria andContactInfoEqualTo(String value) {
            addCriterion("contact_info =", value, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoNotEqualTo(String value) {
            addCriterion("contact_info <>", value, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoGreaterThan(String value) {
            addCriterion("contact_info >", value, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoGreaterThanOrEqualTo(String value) {
            addCriterion("contact_info >=", value, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoLessThan(String value) {
            addCriterion("contact_info <", value, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoLessThanOrEqualTo(String value) {
            addCriterion("contact_info <=", value, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoLike(String value) {
            addCriterion("contact_info like", value, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoNotLike(String value) {
            addCriterion("contact_info not like", value, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoIn(List<String> values) {
            addCriterion("contact_info in", values, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoNotIn(List<String> values) {
            addCriterion("contact_info not in", values, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoBetween(String value1, String value2) {
            addCriterion("contact_info between", value1, value2, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andContactInfoNotBetween(String value1, String value2) {
            addCriterion("contact_info not between", value1, value2, "contactInfo");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("priority is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("priority is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(Integer value) {
            addCriterion("priority =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(Integer value) {
            addCriterion("priority <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(Integer value) {
            addCriterion("priority >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("priority >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(Integer value) {
            addCriterion("priority <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("priority <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<Integer> values) {
            addCriterion("priority in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<Integer> values) {
            addCriterion("priority not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(Integer value1, Integer value2) {
            addCriterion("priority between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("priority not between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNull() {
            addCriterion("assign_time is null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIsNotNull() {
            addCriterion("assign_time is not null");
            return (Criteria) this;
        }

        public Criteria andAssignTimeEqualTo(Date value) {
            addCriterion("assign_time =", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotEqualTo(Date value) {
            addCriterion("assign_time <>", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThan(Date value) {
            addCriterion("assign_time >", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("assign_time >=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThan(Date value) {
            addCriterion("assign_time <", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeLessThanOrEqualTo(Date value) {
            addCriterion("assign_time <=", value, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeIn(List<Date> values) {
            addCriterion("assign_time in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotIn(List<Date> values) {
            addCriterion("assign_time not in", values, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeBetween(Date value1, Date value2) {
            addCriterion("assign_time between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAssignTimeNotBetween(Date value1, Date value2) {
            addCriterion("assign_time not between", value1, value2, "assignTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIsNull() {
            addCriterion("accept_time is null");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIsNotNull() {
            addCriterion("accept_time is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeEqualTo(Date value) {
            addCriterion("accept_time =", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotEqualTo(Date value) {
            addCriterion("accept_time <>", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeGreaterThan(Date value) {
            addCriterion("accept_time >", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("accept_time >=", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeLessThan(Date value) {
            addCriterion("accept_time <", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeLessThanOrEqualTo(Date value) {
            addCriterion("accept_time <=", value, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeIn(List<Date> values) {
            addCriterion("accept_time in", values, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotIn(List<Date> values) {
            addCriterion("accept_time not in", values, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeBetween(Date value1, Date value2) {
            addCriterion("accept_time between", value1, value2, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andAcceptTimeNotBetween(Date value1, Date value2) {
            addCriterion("accept_time not between", value1, value2, "acceptTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeIsNull() {
            addCriterion("rate_time is null");
            return (Criteria) this;
        }

        public Criteria andRateTimeIsNotNull() {
            addCriterion("rate_time is not null");
            return (Criteria) this;
        }

        public Criteria andRateTimeEqualTo(Date value) {
            addCriterion("rate_time =", value, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeNotEqualTo(Date value) {
            addCriterion("rate_time <>", value, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeGreaterThan(Date value) {
            addCriterion("rate_time >", value, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("rate_time >=", value, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeLessThan(Date value) {
            addCriterion("rate_time <", value, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeLessThanOrEqualTo(Date value) {
            addCriterion("rate_time <=", value, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeIn(List<Date> values) {
            addCriterion("rate_time in", values, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeNotIn(List<Date> values) {
            addCriterion("rate_time not in", values, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeBetween(Date value1, Date value2) {
            addCriterion("rate_time between", value1, value2, "rateTime");
            return (Criteria) this;
        }

        public Criteria andRateTimeNotBetween(Date value1, Date value2) {
            addCriterion("rate_time not between", value1, value2, "rateTime");
            return (Criteria) this;
        }

        public Criteria andDealDescIsNull() {
            addCriterion("deal_desc is null");
            return (Criteria) this;
        }

        public Criteria andDealDescIsNotNull() {
            addCriterion("deal_desc is not null");
            return (Criteria) this;
        }

        public Criteria andDealDescEqualTo(String value) {
            addCriterion("deal_desc =", value, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescNotEqualTo(String value) {
            addCriterion("deal_desc <>", value, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescGreaterThan(String value) {
            addCriterion("deal_desc >", value, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescGreaterThanOrEqualTo(String value) {
            addCriterion("deal_desc >=", value, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescLessThan(String value) {
            addCriterion("deal_desc <", value, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescLessThanOrEqualTo(String value) {
            addCriterion("deal_desc <=", value, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescLike(String value) {
            addCriterion("deal_desc like", value, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescNotLike(String value) {
            addCriterion("deal_desc not like", value, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescIn(List<String> values) {
            addCriterion("deal_desc in", values, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescNotIn(List<String> values) {
            addCriterion("deal_desc not in", values, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescBetween(String value1, String value2) {
            addCriterion("deal_desc between", value1, value2, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andDealDescNotBetween(String value1, String value2) {
            addCriterion("deal_desc not between", value1, value2, "dealDesc");
            return (Criteria) this;
        }

        public Criteria andRateLevelIsNull() {
            addCriterion("rate_level is null");
            return (Criteria) this;
        }

        public Criteria andRateLevelIsNotNull() {
            addCriterion("rate_level is not null");
            return (Criteria) this;
        }

        public Criteria andRateLevelEqualTo(Integer value) {
            addCriterion("rate_level =", value, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelNotEqualTo(Integer value) {
            addCriterion("rate_level <>", value, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelGreaterThan(Integer value) {
            addCriterion("rate_level >", value, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("rate_level >=", value, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelLessThan(Integer value) {
            addCriterion("rate_level <", value, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelLessThanOrEqualTo(Integer value) {
            addCriterion("rate_level <=", value, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelIn(List<Integer> values) {
            addCriterion("rate_level in", values, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelNotIn(List<Integer> values) {
            addCriterion("rate_level not in", values, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelBetween(Integer value1, Integer value2) {
            addCriterion("rate_level between", value1, value2, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("rate_level not between", value1, value2, "rateLevel");
            return (Criteria) this;
        }

        public Criteria andRateNumIsNull() {
            addCriterion("rate_num is null");
            return (Criteria) this;
        }

        public Criteria andRateNumIsNotNull() {
            addCriterion("rate_num is not null");
            return (Criteria) this;
        }

        public Criteria andRateNumEqualTo(Integer value) {
            addCriterion("rate_num =", value, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumNotEqualTo(Integer value) {
            addCriterion("rate_num <>", value, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumGreaterThan(Integer value) {
            addCriterion("rate_num >", value, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("rate_num >=", value, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumLessThan(Integer value) {
            addCriterion("rate_num <", value, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumLessThanOrEqualTo(Integer value) {
            addCriterion("rate_num <=", value, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumIn(List<Integer> values) {
            addCriterion("rate_num in", values, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumNotIn(List<Integer> values) {
            addCriterion("rate_num not in", values, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumBetween(Integer value1, Integer value2) {
            addCriterion("rate_num between", value1, value2, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateNumNotBetween(Integer value1, Integer value2) {
            addCriterion("rate_num not between", value1, value2, "rateNum");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentIsNull() {
            addCriterion("rate_attachment is null");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentIsNotNull() {
            addCriterion("rate_attachment is not null");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentEqualTo(String value) {
            addCriterion("rate_attachment =", value, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentNotEqualTo(String value) {
            addCriterion("rate_attachment <>", value, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentGreaterThan(String value) {
            addCriterion("rate_attachment >", value, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentGreaterThanOrEqualTo(String value) {
            addCriterion("rate_attachment >=", value, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentLessThan(String value) {
            addCriterion("rate_attachment <", value, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentLessThanOrEqualTo(String value) {
            addCriterion("rate_attachment <=", value, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentLike(String value) {
            addCriterion("rate_attachment like", value, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentNotLike(String value) {
            addCriterion("rate_attachment not like", value, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentIn(List<String> values) {
            addCriterion("rate_attachment in", values, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentNotIn(List<String> values) {
            addCriterion("rate_attachment not in", values, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentBetween(String value1, String value2) {
            addCriterion("rate_attachment between", value1, value2, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateAttachmentNotBetween(String value1, String value2) {
            addCriterion("rate_attachment not between", value1, value2, "rateAttachment");
            return (Criteria) this;
        }

        public Criteria andRateUsernameIsNull() {
            addCriterion("rate_username is null");
            return (Criteria) this;
        }

        public Criteria andRateUsernameIsNotNull() {
            addCriterion("rate_username is not null");
            return (Criteria) this;
        }

        public Criteria andRateUsernameEqualTo(String value) {
            addCriterion("rate_username =", value, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameNotEqualTo(String value) {
            addCriterion("rate_username <>", value, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameGreaterThan(String value) {
            addCriterion("rate_username >", value, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("rate_username >=", value, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameLessThan(String value) {
            addCriterion("rate_username <", value, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameLessThanOrEqualTo(String value) {
            addCriterion("rate_username <=", value, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameLike(String value) {
            addCriterion("rate_username like", value, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameNotLike(String value) {
            addCriterion("rate_username not like", value, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameIn(List<String> values) {
            addCriterion("rate_username in", values, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameNotIn(List<String> values) {
            addCriterion("rate_username not in", values, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameBetween(String value1, String value2) {
            addCriterion("rate_username between", value1, value2, "rateUsername");
            return (Criteria) this;
        }

        public Criteria andRateUsernameNotBetween(String value1, String value2) {
            addCriterion("rate_username not between", value1, value2, "rateUsername");
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