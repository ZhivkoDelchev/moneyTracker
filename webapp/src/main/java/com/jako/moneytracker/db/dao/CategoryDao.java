package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Jako on 5.4.2015 �..
 */
@Dependent
public class CategoryDao {

    private final TrackerEntityManager trackerEntityManager;

    @Inject
    public CategoryDao(TrackerEntityManager trackerEntityManager) {
        this.trackerEntityManager = trackerEntityManager;
    }

    public List<PaymentCategoryEntity> getCategories() {
        return trackerEntityManager.getResultsForCurrentUser(PaymentCategoryEntity.class, "category");
    }

    public void createCategory(String name) {
        UserEntity user = trackerEntityManager.getCurrentUser();

        PaymentCategoryEntity category = new PaymentCategoryEntity();
        category.setName(name);
        category.setCreator(user);

        trackerEntityManager.persist(category);
    }

    public void deleteCategory(long id) {
        SimpleExpression categoryIdRestriction = Restrictions.eq("id", id);
        PaymentCategoryEntity category = trackerEntityManager.getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", categoryIdRestriction);

        if (category != null) {
            trackerEntityManager.delete(category);
        }
    }
}
