package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.SimpleExpression;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

@Dependent
public class CategoryDao {

    private final TrackerEntityManager trackerEntityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Inject
    public CategoryDao(TrackerEntityManager trackerEntityManager, CriteriaBuilder criteriaBuilder) {
        this.trackerEntityManager = trackerEntityManager;
        this.criteriaBuilder = criteriaBuilder;
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
        SimpleExpression categoryIdRestriction = criteriaBuilder.buildEqualsCriteria("id", id);
        PaymentCategoryEntity category = trackerEntityManager.getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", categoryIdRestriction);

        if (category != null) {
            trackerEntityManager.delete(category);
        }
    }

    public PaymentCategoryEntity findCategoryById(Long categoryId) {
        SimpleExpression equalsCriteria = criteriaBuilder.buildEqualsCriteria("id", categoryId);
        return trackerEntityManager.getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria);
    }
}
