package com.jako.moneytracker.db.dao;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import javax.enterprise.context.Dependent;

@Dependent
public class CriteriaBuilder {

    public SimpleExpression buildEqualsCriteria(String propertyName, Object value) {
        return Restrictions.eq(propertyName, value);
    }
}
