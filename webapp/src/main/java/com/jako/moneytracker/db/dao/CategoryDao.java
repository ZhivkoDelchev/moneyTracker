package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Created by Jako on 5.4.2015 �..
 */
@Dependent
public class CategoryDao {

    @SuppressWarnings("unchecked")
    public List<PaymentCategoryEntity> getCategories(String email, EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        Criteria paymentsCriteria = session.createCriteria(PaymentCategoryEntity.class, "category");
        paymentsCriteria.createAlias("category.creator", "creator");
        paymentsCriteria.add(Restrictions.eq("creator.email", email));

        return paymentsCriteria.list();
    }

    public void createCategory(String name, UserEntity user, EntityManager entityManager) {
        PaymentCategoryEntity category = new PaymentCategoryEntity();
        category.setName(name);
        category.setCreatedDate(new Date());
        category.setCreator(user);

        entityManager.persist(category);
    }
}
