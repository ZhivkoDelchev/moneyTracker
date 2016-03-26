package com.jako.moneytracker.db.dao;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import javax.enterprise.context.Dependent;

@Dependent
public class CriteriaBuilder {

    public SimpleExpression buildEqualsCriteria(String propertyName, Object value) {
        return Restrictions.eq(propertyName, value);
    }

    public Order ascending(final String property) {
        return Order.asc(property);
    }

    public Order descending(final String property) {
        return Order.desc(property);
    }
}
