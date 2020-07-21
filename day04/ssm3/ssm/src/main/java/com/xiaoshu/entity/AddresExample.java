package com.xiaoshu.entity;

import java.util.ArrayList;
import java.util.List;

public class AddresExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AddresExample() {
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

        public Criteria andAddresIdIsNull() {
            addCriterion("addres_id is null");
            return (Criteria) this;
        }

        public Criteria andAddresIdIsNotNull() {
            addCriterion("addres_id is not null");
            return (Criteria) this;
        }

        public Criteria andAddresIdEqualTo(Integer value) {
            addCriterion("addres_id =", value, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdNotEqualTo(Integer value) {
            addCriterion("addres_id <>", value, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdGreaterThan(Integer value) {
            addCriterion("addres_id >", value, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("addres_id >=", value, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdLessThan(Integer value) {
            addCriterion("addres_id <", value, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdLessThanOrEqualTo(Integer value) {
            addCriterion("addres_id <=", value, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdIn(List<Integer> values) {
            addCriterion("addres_id in", values, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdNotIn(List<Integer> values) {
            addCriterion("addres_id not in", values, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdBetween(Integer value1, Integer value2) {
            addCriterion("addres_id between", value1, value2, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresIdNotBetween(Integer value1, Integer value2) {
            addCriterion("addres_id not between", value1, value2, "addresId");
            return (Criteria) this;
        }

        public Criteria andAddresNameIsNull() {
            addCriterion("addres_name is null");
            return (Criteria) this;
        }

        public Criteria andAddresNameIsNotNull() {
            addCriterion("addres_name is not null");
            return (Criteria) this;
        }

        public Criteria andAddresNameEqualTo(String value) {
            addCriterion("addres_name =", value, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameNotEqualTo(String value) {
            addCriterion("addres_name <>", value, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameGreaterThan(String value) {
            addCriterion("addres_name >", value, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameGreaterThanOrEqualTo(String value) {
            addCriterion("addres_name >=", value, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameLessThan(String value) {
            addCriterion("addres_name <", value, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameLessThanOrEqualTo(String value) {
            addCriterion("addres_name <=", value, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameLike(String value) {
            addCriterion("addres_name like", value, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameNotLike(String value) {
            addCriterion("addres_name not like", value, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameIn(List<String> values) {
            addCriterion("addres_name in", values, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameNotIn(List<String> values) {
            addCriterion("addres_name not in", values, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameBetween(String value1, String value2) {
            addCriterion("addres_name between", value1, value2, "addresName");
            return (Criteria) this;
        }

        public Criteria andAddresNameNotBetween(String value1, String value2) {
            addCriterion("addres_name not between", value1, value2, "addresName");
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