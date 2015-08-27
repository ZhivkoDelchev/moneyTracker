package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Jako on 5.4.2015 �..
 */
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
        List payments = mock(List.class);

        when(trackerEntityManager.getResultsForCurrentUser(PaymentCategoryEntity.class, "category")).thenReturn(payments);

        List<PaymentCategoryEntity> result = sut.getCategories();

        assertEquals(payments, result);
        verify(trackerEntityManager).getResultsForCurrentUser(PaymentCategoryEntity.class, "category");
    }

    @Test
    public void shouldCreateCategory() throws Exception {
        UserEntity user = mock(UserEntity.class);

        when(trackerEntityManager.getCurrentUser()).thenReturn(user);

        sut.createCategory("name");

        // TODO: verify if proper category is persisted.
        verify(trackerEntityManager).persist(any(PaymentCategoryEntity.class));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        long id = 1;
        PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        SimpleExpression equalsCriteria = mock(SimpleExpression.class);
        when(criteriaBuilder.buildEqualsCriteria("id", id)).thenReturn(equalsCriteria);

        when(trackerEntityManager.getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria)).thenReturn(category);

        sut.deleteCategory(id);

        verify(trackerEntityManager).getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria);
        verify(trackerEntityManager).delete(category);
    }

    @Test
    public void testFindCategoryById() throws Exception {
        long id = 1;

        SimpleExpression equalsCriteria = mock(SimpleExpression.class);
        when(criteriaBuilder.buildEqualsCriteria("id", id)).thenReturn(equalsCriteria);

        PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        when(trackerEntityManager.getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria)).thenReturn(category);

        PaymentCategoryEntity result = sut.findCategoryById(id);

        verify(trackerEntityManager).getUniqueResultForCurrentUser(PaymentCategoryEntity.class, "category", equalsCriteria);
        assertEquals(category, result);
    }
}