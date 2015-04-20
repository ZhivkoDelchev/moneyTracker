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
 * Created by Jako on 5.4.2015 ã..
 */
@Dependent
public class CategoryDao {

    @SuppressWarnings("unchecked")
    public List<PaymentCategoryEntity> getCategories(String email, EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        Criteria categoryCriteria = session.createCriteria(PaymentCategoryEntity.class, "category");
        categoryCriteria.createAlias("category.creator", "creator");
        categoryCriteria.add(Restrictions.eq("creator.email", email));

        return categoryCriteria.list();
    }

    public void createCategory(String name, UserEntity user, EntityManager entityManager) {
        PaymentCategoryEntity category = new PaymentCategoryEntity();
        category.setName(name);
        category.setCreatedDate(new Date());
        category.setCreator(user);

        entityManager.persist(category);
    }

    public void deleteCategory(long id, String email, EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        Criteria categoryCriteria = session.createCriteria(PaymentCategoryEntity.class, "category");
        categoryCriteria.add(Restrictions.eq("id", id));
        categoryCriteria.createAlias("category.creator", "creator");
        categoryCriteria.add(Restrictions.eq("creator.email", email));

        PaymentCategoryEntity category = (PaymentCategoryEntity) categoryCriteria.uniqueResult();
        if (category != null) {
            session.delete(category);
        }
    }
}
