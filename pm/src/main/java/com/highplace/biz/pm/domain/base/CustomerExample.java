package com.highplace.biz.pm.domain.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CustomerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustomerExample() {
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

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(Long value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(Long value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(Long value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(Long value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(Long value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<Long> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<Long> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(Long value1, Long value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(Long value1, Long value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
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

        public Criteria andCustomerNameIsNull() {
            addCriterion("customer_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNotNull() {
            addCriterion("customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameEqualTo(String value) {
            addCriterion("customer_name =", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotEqualTo(String value) {
            addCriterion("customer_name <>", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThan(String value) {
            addCriterion("customer_name >", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("customer_name >=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThan(String value) {
            addCriterion("customer_name <", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("customer_name <=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLike(String value) {
            addCriterion("customer_name like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotLike(String value) {
            addCriterion("customer_name not like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIn(List<String> values) {
            addCriterion("customer_name in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotIn(List<String> values) {
            addCriterion("customer_name not in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameBetween(String value1, String value2) {
            addCriterion("customer_name between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotBetween(String value1, String value2) {
            addCriterion("customer_name not between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeIsNull() {
            addCriterion("identity_type is null");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeIsNotNull() {
            addCriterion("identity_type is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeEqualTo(Integer value) {
            addCriterion("identity_type =", value, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeNotEqualTo(Integer value) {
            addCriterion("identity_type <>", value, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeGreaterThan(Integer value) {
            addCriterion("identity_type >", value, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("identity_type >=", value, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeLessThan(Integer value) {
            addCriterion("identity_type <", value, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeLessThanOrEqualTo(Integer value) {
            addCriterion("identity_type <=", value, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeIn(List<Integer> values) {
            addCriterion("identity_type in", values, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeNotIn(List<Integer> values) {
            addCriterion("identity_type not in", values, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeBetween(Integer value1, Integer value2) {
            addCriterion("identity_type between", value1, value2, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("identity_type not between", value1, value2, "identityType");
            return (Criteria) this;
        }

        public Criteria andIdentityNoIsNull() {
            addCriterion("identity_no is null");
            return (Criteria) this;
        }

        public Criteria andIdentityNoIsNotNull() {
            addCriterion("identity_no is not null");
            return (Criteria) this;
        }

        public Criteria andIdentityNoEqualTo(String value) {
            addCriterion("identity_no =", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoNotEqualTo(String value) {
            addCriterion("identity_no <>", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoGreaterThan(String value) {
            addCriterion("identity_no >", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoGreaterThanOrEqualTo(String value) {
            addCriterion("identity_no >=", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoLessThan(String value) {
            addCriterion("identity_no <", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoLessThanOrEqualTo(String value) {
            addCriterion("identity_no <=", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoLike(String value) {
            addCriterion("identity_no like", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoNotLike(String value) {
            addCriterion("identity_no not like", value, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoIn(List<String> values) {
            addCriterion("identity_no in", values, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoNotIn(List<String> values) {
            addCriterion("identity_no not in", values, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoBetween(String value1, String value2) {
            addCriterion("identity_no between", value1, value2, "identityNo");
            return (Criteria) this;
        }

        public Criteria andIdentityNoNotBetween(String value1, String value2) {
            addCriterion("identity_no not between", value1, value2, "identityNo");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andAliasNameIsNull() {
            addCriterion("alias_name is null");
            return (Criteria) this;
        }

        public Criteria andAliasNameIsNotNull() {
            addCriterion("alias_name is not null");
            return (Criteria) this;
        }

        public Criteria andAliasNameEqualTo(String value) {
            addCriterion("alias_name =", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameNotEqualTo(String value) {
            addCriterion("alias_name <>", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameGreaterThan(String value) {
            addCriterion("alias_name >", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameGreaterThanOrEqualTo(String value) {
            addCriterion("alias_name >=", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameLessThan(String value) {
            addCriterion("alias_name <", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameLessThanOrEqualTo(String value) {
            addCriterion("alias_name <=", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameLike(String value) {
            addCriterion("alias_name like", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameNotLike(String value) {
            addCriterion("alias_name not like", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameIn(List<String> values) {
            addCriterion("alias_name in", values, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameNotIn(List<String> values) {
            addCriterion("alias_name not in", values, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameBetween(String value1, String value2) {
            addCriterion("alias_name between", value1, value2, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameNotBetween(String value1, String value2) {
            addCriterion("alias_name not between", value1, value2, "aliasName");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andWechatIsNull() {
            addCriterion("wechat is null");
            return (Criteria) this;
        }

        public Criteria andWechatIsNotNull() {
            addCriterion("wechat is not null");
            return (Criteria) this;
        }

        public Criteria andWechatEqualTo(String value) {
            addCriterion("wechat =", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatNotEqualTo(String value) {
            addCriterion("wechat <>", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatGreaterThan(String value) {
            addCriterion("wechat >", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatGreaterThanOrEqualTo(String value) {
            addCriterion("wechat >=", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatLessThan(String value) {
            addCriterion("wechat <", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatLessThanOrEqualTo(String value) {
            addCriterion("wechat <=", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatLike(String value) {
            addCriterion("wechat like", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatNotLike(String value) {
            addCriterion("wechat not like", value, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatIn(List<String> values) {
            addCriterion("wechat in", values, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatNotIn(List<String> values) {
            addCriterion("wechat not in", values, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatBetween(String value1, String value2) {
            addCriterion("wechat between", value1, value2, "wechat");
            return (Criteria) this;
        }

        public Criteria andWechatNotBetween(String value1, String value2) {
            addCriterion("wechat not between", value1, value2, "wechat");
            return (Criteria) this;
        }

        public Criteria andLangIsNull() {
            addCriterion("lang is null");
            return (Criteria) this;
        }

        public Criteria andLangIsNotNull() {
            addCriterion("lang is not null");
            return (Criteria) this;
        }

        public Criteria andLangEqualTo(String value) {
            addCriterion("lang =", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangNotEqualTo(String value) {
            addCriterion("lang <>", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangGreaterThan(String value) {
            addCriterion("lang >", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangGreaterThanOrEqualTo(String value) {
            addCriterion("lang >=", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangLessThan(String value) {
            addCriterion("lang <", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangLessThanOrEqualTo(String value) {
            addCriterion("lang <=", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangLike(String value) {
            addCriterion("lang like", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangNotLike(String value) {
            addCriterion("lang not like", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangIn(List<String> values) {
            addCriterion("lang in", values, "lang");
            return (Criteria) this;
        }

        public Criteria andLangNotIn(List<String> values) {
            addCriterion("lang not in", values, "lang");
            return (Criteria) this;
        }

        public Criteria andLangBetween(String value1, String value2) {
            addCriterion("lang between", value1, value2, "lang");
            return (Criteria) this;
        }

        public Criteria andLangNotBetween(String value1, String value2) {
            addCriterion("lang not between", value1, value2, "lang");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(String value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(String value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(String value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(String value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(String value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(String value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLike(String value) {
            addCriterion("gender like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotLike(String value) {
            addCriterion("gender not like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<String> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<String> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(String value1, String value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(String value1, String value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andBirthIsNull() {
            addCriterion("birth is null");
            return (Criteria) this;
        }

        public Criteria andBirthIsNotNull() {
            addCriterion("birth is not null");
            return (Criteria) this;
        }

        public Criteria andBirthEqualTo(Date value) {
            addCriterionForJDBCDate("birth =", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotEqualTo(Date value) {
            addCriterionForJDBCDate("birth <>", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThan(Date value) {
            addCriterionForJDBCDate("birth >", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth >=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThan(Date value) {
            addCriterionForJDBCDate("birth <", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("birth <=", value, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthIn(List<Date> values) {
            addCriterionForJDBCDate("birth in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotIn(List<Date> values) {
            addCriterionForJDBCDate("birth not in", values, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andBirthNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("birth not between", value1, value2, "birth");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateIsNull() {
            addCriterion("ident_start_date is null");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateIsNotNull() {
            addCriterion("ident_start_date is not null");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("ident_start_date =", value, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("ident_start_date <>", value, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("ident_start_date >", value, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ident_start_date >=", value, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateLessThan(Date value) {
            addCriterionForJDBCDate("ident_start_date <", value, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ident_start_date <=", value, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("ident_start_date in", values, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("ident_start_date not in", values, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ident_start_date between", value1, value2, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ident_start_date not between", value1, value2, "identStartDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateIsNull() {
            addCriterion("ident_end_date is null");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateIsNotNull() {
            addCriterion("ident_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("ident_end_date =", value, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("ident_end_date <>", value, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("ident_end_date >", value, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ident_end_date >=", value, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateLessThan(Date value) {
            addCriterionForJDBCDate("ident_end_date <", value, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("ident_end_date <=", value, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("ident_end_date in", values, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("ident_end_date not in", values, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ident_end_date between", value1, value2, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("ident_end_date not between", value1, value2, "identEndDate");
            return (Criteria) this;
        }

        public Criteria andIdentPicIsNull() {
            addCriterion("ident_pic is null");
            return (Criteria) this;
        }

        public Criteria andIdentPicIsNotNull() {
            addCriterion("ident_pic is not null");
            return (Criteria) this;
        }

        public Criteria andIdentPicEqualTo(String value) {
            addCriterion("ident_pic =", value, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicNotEqualTo(String value) {
            addCriterion("ident_pic <>", value, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicGreaterThan(String value) {
            addCriterion("ident_pic >", value, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicGreaterThanOrEqualTo(String value) {
            addCriterion("ident_pic >=", value, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicLessThan(String value) {
            addCriterion("ident_pic <", value, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicLessThanOrEqualTo(String value) {
            addCriterion("ident_pic <=", value, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicLike(String value) {
            addCriterion("ident_pic like", value, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicNotLike(String value) {
            addCriterion("ident_pic not like", value, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicIn(List<String> values) {
            addCriterion("ident_pic in", values, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicNotIn(List<String> values) {
            addCriterion("ident_pic not in", values, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicBetween(String value1, String value2) {
            addCriterion("ident_pic between", value1, value2, "identPic");
            return (Criteria) this;
        }

        public Criteria andIdentPicNotBetween(String value1, String value2) {
            addCriterion("ident_pic not between", value1, value2, "identPic");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1IsNull() {
            addCriterion("backup_phone_1 is null");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1IsNotNull() {
            addCriterion("backup_phone_1 is not null");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1EqualTo(String value) {
            addCriterion("backup_phone_1 =", value, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1NotEqualTo(String value) {
            addCriterion("backup_phone_1 <>", value, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1GreaterThan(String value) {
            addCriterion("backup_phone_1 >", value, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1GreaterThanOrEqualTo(String value) {
            addCriterion("backup_phone_1 >=", value, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1LessThan(String value) {
            addCriterion("backup_phone_1 <", value, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1LessThanOrEqualTo(String value) {
            addCriterion("backup_phone_1 <=", value, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1Like(String value) {
            addCriterion("backup_phone_1 like", value, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1NotLike(String value) {
            addCriterion("backup_phone_1 not like", value, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1In(List<String> values) {
            addCriterion("backup_phone_1 in", values, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1NotIn(List<String> values) {
            addCriterion("backup_phone_1 not in", values, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1Between(String value1, String value2) {
            addCriterion("backup_phone_1 between", value1, value2, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone1NotBetween(String value1, String value2) {
            addCriterion("backup_phone_1 not between", value1, value2, "backupPhone1");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2IsNull() {
            addCriterion("backup_phone_2 is null");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2IsNotNull() {
            addCriterion("backup_phone_2 is not null");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2EqualTo(String value) {
            addCriterion("backup_phone_2 =", value, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2NotEqualTo(String value) {
            addCriterion("backup_phone_2 <>", value, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2GreaterThan(String value) {
            addCriterion("backup_phone_2 >", value, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2GreaterThanOrEqualTo(String value) {
            addCriterion("backup_phone_2 >=", value, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2LessThan(String value) {
            addCriterion("backup_phone_2 <", value, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2LessThanOrEqualTo(String value) {
            addCriterion("backup_phone_2 <=", value, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2Like(String value) {
            addCriterion("backup_phone_2 like", value, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2NotLike(String value) {
            addCriterion("backup_phone_2 not like", value, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2In(List<String> values) {
            addCriterion("backup_phone_2 in", values, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2NotIn(List<String> values) {
            addCriterion("backup_phone_2 not in", values, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2Between(String value1, String value2) {
            addCriterion("backup_phone_2 between", value1, value2, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andBackupPhone2NotBetween(String value1, String value2) {
            addCriterion("backup_phone_2 not between", value1, value2, "backupPhone2");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameIsNull() {
            addCriterion("emergency_contact_name is null");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameIsNotNull() {
            addCriterion("emergency_contact_name is not null");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameEqualTo(String value) {
            addCriterion("emergency_contact_name =", value, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameNotEqualTo(String value) {
            addCriterion("emergency_contact_name <>", value, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameGreaterThan(String value) {
            addCriterion("emergency_contact_name >", value, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameGreaterThanOrEqualTo(String value) {
            addCriterion("emergency_contact_name >=", value, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameLessThan(String value) {
            addCriterion("emergency_contact_name <", value, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameLessThanOrEqualTo(String value) {
            addCriterion("emergency_contact_name <=", value, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameLike(String value) {
            addCriterion("emergency_contact_name like", value, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameNotLike(String value) {
            addCriterion("emergency_contact_name not like", value, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameIn(List<String> values) {
            addCriterion("emergency_contact_name in", values, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameNotIn(List<String> values) {
            addCriterion("emergency_contact_name not in", values, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameBetween(String value1, String value2) {
            addCriterion("emergency_contact_name between", value1, value2, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactNameNotBetween(String value1, String value2) {
            addCriterion("emergency_contact_name not between", value1, value2, "emergencyContactName");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneIsNull() {
            addCriterion("emergency_contact_phone is null");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneIsNotNull() {
            addCriterion("emergency_contact_phone is not null");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneEqualTo(String value) {
            addCriterion("emergency_contact_phone =", value, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneNotEqualTo(String value) {
            addCriterion("emergency_contact_phone <>", value, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneGreaterThan(String value) {
            addCriterion("emergency_contact_phone >", value, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("emergency_contact_phone >=", value, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneLessThan(String value) {
            addCriterion("emergency_contact_phone <", value, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneLessThanOrEqualTo(String value) {
            addCriterion("emergency_contact_phone <=", value, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneLike(String value) {
            addCriterion("emergency_contact_phone like", value, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneNotLike(String value) {
            addCriterion("emergency_contact_phone not like", value, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneIn(List<String> values) {
            addCriterion("emergency_contact_phone in", values, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneNotIn(List<String> values) {
            addCriterion("emergency_contact_phone not in", values, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneBetween(String value1, String value2) {
            addCriterion("emergency_contact_phone between", value1, value2, "emergencyContactPhone");
            return (Criteria) this;
        }

        public Criteria andEmergencyContactPhoneNotBetween(String value1, String value2) {
            addCriterion("emergency_contact_phone not between", value1, value2, "emergencyContactPhone");
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