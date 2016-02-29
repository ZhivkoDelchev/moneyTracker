package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class CategoryDaoTest {

    private CategoryDao sut;

    @Mock private TrackerEntityManager trackerEntityManager;
    @Mock private CriteriaBuilder criteriaBuilder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new CategoryDao(trackerEntityManager, criteriaBuilder);
    }

    @Test
    public void testCreateCriteriaForListingAllPaymentsForAGivenUser() throws Exception {
        List<PaymentCategoryEntity> payments = Mockito.mock(List.class);

        Mockito.when(trackerEntityManager.getResultsForCurrentUser(PaymentCategoryEntity.class, "category")).thenReturn(payments);

        List<PaymentCategoryEntity> result = sut.getCategories();

        Assert.assertEquals(payments, result);
        Mockito.verify(trackerEntityManager).getResultsForCurrentUser(PaymentCategoryEntity.class, "category");
    }

    @Test
    public void shouldCreateCategory() throws Exception {
        UserEntity user = Mockito.mock(UserEntity.class);

        Mockito.when(trackerEntityManager.getCurrentUser()).thenReturn(user);

        sut.createCategory("name");

        // TODO: verify if proper category is persisted.
        Mockito.verify(trackerEntityManager).persist(Matchers.any(PaymentCategoryEntity.class));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        long id = 1;
        PaymentCategoryEntity category = Mockito.mock(PaymentCategoryEntity.class);

        SimpleExpression equalsCriteria = Mockito.mock(SimpleExpression.class);
        Mockito.when(criteriaBuilder.buildEqualsCriteria("id", id)).thenReturn(equalsCriteria);

        Mockito.when(trackerEntityManager.getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria)).thenReturn(category);

        sut.deleteCategory(id);

        Mockito.verify(trackerEntityManager).getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria);
        Mockito.verify(trackerEntityManager).delete(category);
    }

    @Test
    public void testFindCategoryById() throws Exception {
        long id = 1;

        SimpleExpression equalsCriteria = Mockito.mock(SimpleExpression.class);
        Mockito.when(criteriaBuilder.buildEqualsCriteria("id", id)).thenReturn(equalsCriteria);

        PaymentCategoryEntity category = Mockito.mock(PaymentCategoryEntity.class);
        Mockito.when(trackerEntityManager.getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria)).thenReturn(category);

        PaymentCategoryEntity result = sut.findCategoryById(id);

        Mockito.verify(trackerEntityManager).getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria);
        Assert.assertEquals(category, result);
    }
}