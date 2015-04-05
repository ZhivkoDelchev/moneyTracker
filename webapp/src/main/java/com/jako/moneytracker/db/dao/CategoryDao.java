package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Jako on 5.4.2015 ã..
 */
@Dependent
public class CategoryDao {

    public List<PaymentCategoryEntity> getCategories(String email, EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        Criteria paymentsCriteria = session.createCriteria(PaymentCategoryEntity.class, "category");
        paymentsCriteria.createAlias("category.creator", "creator");
        paymentsCriteria.add(Restrictions.eq("creator.email", email));

        List<PaymentCategoryEntity> payments = paymentsCriteria.list();

        return payments;
    }
}
