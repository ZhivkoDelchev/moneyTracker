package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentDaoTest {

    @InjectMocks
    private PaymentDao sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new PaymentDao();
    }

    @Test
    public void testCreateCriteriaForListingAllPaymentsForAGivenUser() throws Exception {
        UserEntity user = mock(UserEntity.class);
        when(user.getEmail()).thenReturn("email");
        List payments = mock(List.class);

        Criteria criteria = mock(Criteria.class);
        when(criteria.list()).thenReturn(payments);

        Session session = mock(Session.class);
        when(session.createCriteria(PaymentEntity.class, "payment")).thenReturn(criteria);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        List<PaymentEntity> result = sut.getUserPayments(user.getEmail(), entityManager);

        assertEquals(payments, result);
    }
}