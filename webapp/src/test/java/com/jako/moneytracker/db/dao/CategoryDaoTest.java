package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jako on 5.4.2015 ã..
 */
public class CategoryDaoTest {

    private CategoryDao sut;

    @Before
    public void setUp() throws Exception {
        sut = new CategoryDao();
    }

    @Test
    public void testCreateCriteriaForListingAllPaymentsForAGivenUser() throws Exception {
        UserEntity user = mock(UserEntity.class);
        when(user.getEmail()).thenReturn("email");
        List payments = mock(List.class);

        Criteria criteria = mock(Criteria.class);
        when(criteria.list()).thenReturn(payments);

        Session session = mock(Session.class);
        when(session.createCriteria(PaymentCategoryEntity.class, "category")).thenReturn(criteria);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        List<PaymentCategoryEntity> result = sut.getCategories(user.getEmail(), entityManager);

        assertEquals(payments, result);
    }

}