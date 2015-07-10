package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jako on 5.4.2015 �..
 */
public class CategoryDaoTest {

    private CategoryDao sut;

    @Mock private TrackerEntityManager trackerEntityManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new CategoryDao(trackerEntityManager);
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
        EntityManager entityManager = mock(EntityManager.class);

        sut.createCategory("name", user, entityManager);
    }
}