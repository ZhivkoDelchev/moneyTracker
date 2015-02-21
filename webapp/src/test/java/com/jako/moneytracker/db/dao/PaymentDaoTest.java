package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.hibernate.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentDaoTest {

    @Mock
    private HibernateUtils hibernateUtils;

    @InjectMocks
    private PaymentDao sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new PaymentDao();
        sut.setHibernateUtils(hibernateUtils);
    }

    @Test
    public void testCreateCriteriaForListingAllPaymentsForAGivenUser() throws Exception {
        UserEntity user = mock(UserEntity.class);
        Transaction transaction = mock(Transaction.class);
        List payments = mock(List.class);

        Criteria criteria = mock(Criteria.class);
        when(criteria.list()).thenReturn(payments);

        Session session = mock(Session.class);
        when(session.createCriteria(PaymentEntity.class)).thenReturn(criteria);

        when(hibernateUtils.getCurrentSession()).thenReturn(session);
        when(hibernateUtils.beginTransaction(session)).thenReturn(transaction);

        List<PaymentEntity> result = sut.getUserPayments(user);

        assertEquals(payments, result);
    }
}