package com.highplace.biz.pm.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class NoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NoticeExample() {
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

        public Criteria andNoticeIdIsNull() {
            addCriterion("notice_id is null");
            return (Criteria) this;
        }

        public Criteria andNoticeIdIsNotNull() {
            addCriterion("notice_id is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeIdEqualTo(Long value) {
            addCriterion("notice_id =", value, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdNotEqualTo(Long value) {
            addCriterion("notice_id <>", value, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdGreaterThan(Long value) {
            addCriterion("notice_id >", value, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("notice_id >=", value, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdLessThan(Long value) {
            addCriterion("notice_id <", value, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdLessThanOrEqualTo(Long value) {
            addCriterion("notice_id <=", value, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdIn(List<Long> values) {
            addCriterion("notice_id in", values, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdNotIn(List<Long> values) {
            addCriterion("notice_id not in", values, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdBetween(Long value1, Long value2) {
            addCriterion("notice_id between", value1, value2, "noticeId");
            return (Criteria) this;
        }

        public Criteria andNoticeIdNotBetween(Long value1, Long value2) {
            addCriterion("notice_id not between", value1, value2, "noticeId");
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
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

        public Criteria andPublishDateIsNull() {
            addCriterion("publish_date is null");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNotNull() {
            addCriterion("publish_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublishDateEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date =", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date <>", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThan(Date value) {
            addCriterionForJDBCDate("publish_date >", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date >=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThan(Date value) {
            addCriterionForJDBCDate("publish_date <", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date <=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateIn(List<Date> values) {
            addCriterionForJDBCDate("publish_date in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("publish_date not in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("publish_date between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("publish_date not between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andValidDateIsNull() {
            addCriterion("valid_date is null");
            return (Criteria) this;
        }

        public Criteria andValidDateIsNotNull() {
            addCriterion("valid_date is not null");
            return (Criteria) this;
        }

        public Criteria andValidDateEqualTo(Date value) {
            addCriterionForJDBCDate("valid_date =", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("valid_date <>", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateGreaterThan(Date value) {
            addCriterionForJDBCDate("valid_date >", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("valid_date >=", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateLessThan(Date value) {
            addCriterionForJDBCDate("valid_date <", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("valid_date <=", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateIn(List<Date> values) {
            addCriterionForJDBCDate("valid_date in", values, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("valid_date not in", values, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("valid_date between", value1, value2, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("valid_date not between", value1, value2, "validDate");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNull() {
            addCriterion("publisher is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNotNull() {
            addCriterion("publisher is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherEqualTo(String value) {
            addCriterion("publisher =", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotEqualTo(String value) {
            addCriterion("publisher <>", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThan(String value) {
            addCriterion("publisher >", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThanOrEqualTo(String value) {
            addCriterion("publisher >=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThan(String value) {
            addCriterion("publisher <", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThanOrEqualTo(String value) {
            addCriterion("publisher <=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLike(String value) {
            addCriterion("publisher like", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotLike(String value) {
            addCriterion("publisher not like", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherIn(List<String> values) {
            addCriterion("publisher in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotIn(List<String> values) {
            addCriterion("publisher not in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherBetween(String value1, String value2) {
            addCriterion("publisher between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotBetween(String value1, String value2) {
            addCriterion("publisher not between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andApproverIsNull() {
            addCriterion("approver is null");
            return (Criteria) this;
        }

        public Criteria andApproverIsNotNull() {
            addCriterion("approver is not null");
            return (Criteria) this;
        }

        public Criteria andApproverEqualTo(String value) {
            addCriterion("approver =", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotEqualTo(String value) {
            addCriterion("approver <>", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverGreaterThan(String value) {
            addCriterion("approver >", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverGreaterThanOrEqualTo(String value) {
            addCriterion("approver >=", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLessThan(String value) {
            addCriterion("approver <", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLessThanOrEqualTo(String value) {
            addCriterion("approver <=", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLike(String value) {
            addCriterion("approver like", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotLike(String value) {
            addCriterion("approver not like", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverIn(List<String> values) {
            addCriterion("approver in", values, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotIn(List<String> values) {
            addCriterion("approver not in", values, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverBetween(String value1, String value2) {
            addCriterion("approver between", value1, value2, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotBetween(String value1, String value2) {
            addCriterion("approver not between", value1, value2, "approver");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameIsNull() {
            addCriterion("attachment_name is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameIsNotNull() {
            addCriterion("attachment_name is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameEqualTo(String value) {
            addCriterion("attachment_name =", value, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameNotEqualTo(String value) {
            addCriterion("attachment_name <>", value, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameGreaterThan(String value) {
            addCriterion("attachment_name >", value, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameGreaterThanOrEqualTo(String value) {
            addCriterion("attachment_name >=", value, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameLessThan(String value) {
            addCriterion("attachment_name <", value, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameLessThanOrEqualTo(String value) {
            addCriterion("attachment_name <=", value, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameLike(String value) {
            addCriterion("attachment_name like", value, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameNotLike(String value) {
            addCriterion("attachment_name not like", value, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameIn(List<String> values) {
            addCriterion("attachment_name in", values, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameNotIn(List<String> values) {
            addCriterion("attachment_name not in", values, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameBetween(String value1, String value2) {
            addCriterion("attachment_name between", value1, value2, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentNameNotBetween(String value1, String value2) {
            addCriterion("attachment_name not between", value1, value2, "attachmentName");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkIsNull() {
            addCriterion("attachment_link is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkIsNotNull() {
            addCriterion("attachment_link is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkEqualTo(String value) {
            addCriterion("attachment_link =", value, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkNotEqualTo(String value) {
            addCriterion("attachment_link <>", value, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkGreaterThan(String value) {
            addCriterion("attachment_link >", value, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkGreaterThanOrEqualTo(String value) {
            addCriterion("attachment_link >=", value, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkLessThan(String value) {
            addCriterion("attachment_link <", value, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkLessThanOrEqualTo(String value) {
            addCriterion("attachment_link <=", value, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkLike(String value) {
            addCriterion("attachment_link like", value, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkNotLike(String value) {
            addCriterion("attachment_link not like", value, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkIn(List<String> values) {
            addCriterion("attachment_link in", values, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkNotIn(List<String> values) {
            addCriterion("attachment_link not in", values, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkBetween(String value1, String value2) {
            addCriterion("attachment_link between", value1, value2, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andAttachmentLinkNotBetween(String value1, String value2) {
            addCriterion("attachment_link not between", value1, value2, "attachmentLink");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListIsNull() {
            addCriterion("specify_zone_id_list is null");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListIsNotNull() {
            addCriterion("specify_zone_id_list is not null");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListEqualTo(String value) {
            addCriterion("specify_zone_id_list =", value, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListNotEqualTo(String value) {
            addCriterion("specify_zone_id_list <>", value, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListGreaterThan(String value) {
            addCriterion("specify_zone_id_list >", value, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListGreaterThanOrEqualTo(String value) {
            addCriterion("specify_zone_id_list >=", value, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListLessThan(String value) {
            addCriterion("specify_zone_id_list <", value, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListLessThanOrEqualTo(String value) {
            addCriterion("specify_zone_id_list <=", value, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListLike(String value) {
            addCriterion("specify_zone_id_list like", value, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListNotLike(String value) {
            addCriterion("specify_zone_id_list not like", value, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListIn(List<String> values) {
            addCriterion("specify_zone_id_list in", values, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListNotIn(List<String> values) {
            addCriterion("specify_zone_id_list not in", values, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListBetween(String value1, String value2) {
            addCriterion("specify_zone_id_list between", value1, value2, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyZoneIdListNotBetween(String value1, String value2) {
            addCriterion("specify_zone_id_list not between", value1, value2, "specifyZoneIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListIsNull() {
            addCriterion("specify_building_id_list is null");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListIsNotNull() {
            addCriterion("specify_building_id_list is not null");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListEqualTo(String value) {
            addCriterion("specify_building_id_list =", value, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListNotEqualTo(String value) {
            addCriterion("specify_building_id_list <>", value, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListGreaterThan(String value) {
            addCriterion("specify_building_id_list >", value, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListGreaterThanOrEqualTo(String value) {
            addCriterion("specify_building_id_list >=", value, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListLessThan(String value) {
            addCriterion("specify_building_id_list <", value, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListLessThanOrEqualTo(String value) {
            addCriterion("specify_building_id_list <=", value, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListLike(String value) {
            addCriterion("specify_building_id_list like", value, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListNotLike(String value) {
            addCriterion("specify_building_id_list not like", value, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListIn(List<String> values) {
            addCriterion("specify_building_id_list in", values, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListNotIn(List<String> values) {
            addCriterion("specify_building_id_list not in", values, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListBetween(String value1, String value2) {
            addCriterion("specify_building_id_list between", value1, value2, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyBuildingIdListNotBetween(String value1, String value2) {
            addCriterion("specify_building_id_list not between", value1, value2, "specifyBuildingIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListIsNull() {
            addCriterion("specify_unit_id_list is null");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListIsNotNull() {
            addCriterion("specify_unit_id_list is not null");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListEqualTo(String value) {
            addCriterion("specify_unit_id_list =", value, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListNotEqualTo(String value) {
            addCriterion("specify_unit_id_list <>", value, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListGreaterThan(String value) {
            addCriterion("specify_unit_id_list >", value, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListGreaterThanOrEqualTo(String value) {
            addCriterion("specify_unit_id_list >=", value, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListLessThan(String value) {
            addCriterion("specify_unit_id_list <", value, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListLessThanOrEqualTo(String value) {
            addCriterion("specify_unit_id_list <=", value, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListLike(String value) {
            addCriterion("specify_unit_id_list like", value, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListNotLike(String value) {
            addCriterion("specify_unit_id_list not like", value, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListIn(List<String> values) {
            addCriterion("specify_unit_id_list in", values, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListNotIn(List<String> values) {
            addCriterion("specify_unit_id_list not in", values, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListBetween(String value1, String value2) {
            addCriterion("specify_unit_id_list between", value1, value2, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyUnitIdListNotBetween(String value1, String value2) {
            addCriterion("specify_unit_id_list not between", value1, value2, "specifyUnitIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListIsNull() {
            addCriterion("specify_room_id_list is null");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListIsNotNull() {
            addCriterion("specify_room_id_list is not null");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListEqualTo(String value) {
            addCriterion("specify_room_id_list =", value, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListNotEqualTo(String value) {
            addCriterion("specify_room_id_list <>", value, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListGreaterThan(String value) {
            addCriterion("specify_room_id_list >", value, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListGreaterThanOrEqualTo(String value) {
            addCriterion("specify_room_id_list >=", value, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListLessThan(String value) {
            addCriterion("specify_room_id_list <", value, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListLessThanOrEqualTo(String value) {
            addCriterion("specify_room_id_list <=", value, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListLike(String value) {
            addCriterion("specify_room_id_list like", value, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListNotLike(String value) {
            addCriterion("specify_room_id_list not like", value, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListIn(List<String> values) {
            addCriterion("specify_room_id_list in", values, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListNotIn(List<String> values) {
            addCriterion("specify_room_id_list not in", values, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListBetween(String value1, String value2) {
            addCriterion("specify_room_id_list between", value1, value2, "specifyRoomIdList");
            return (Criteria) this;
        }

        public Criteria andSpecifyRoomIdListNotBetween(String value1, String value2) {
            addCriterion("specify_room_id_list not between", value1, value2, "specifyRoomIdList");
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