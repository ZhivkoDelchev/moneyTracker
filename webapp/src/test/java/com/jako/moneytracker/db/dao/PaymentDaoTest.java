package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.Session;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentDaoTest {

    private PaymentDao sut;
    @Mock private TrackerEntityManager trackerEntityManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new PaymentDao(trackerEntityManager);
    }

    @Test
    public void testCreateCriteriaForListingAllPaymentsForAGivenUser() throws Exception {
        List<PaymentEntity> payments = mock(List.class);
        when(trackerEntityManager.getResultsForCurrentUser(PaymentEntity.class, "payment")).thenReturn(payments);

        List<PaymentEntity> result = sut.getUserPayments();

        assertEquals(payments, result);
        verify(trackerEntityManager).getResultsForCurrentUser(PaymentEntity.class, "payment");
    }

    @Test
    public void shouldRemoveCategoriesFromAllPaymentsAssociatedToGivenCategory() throws Exception {
        Session session = mock(Session.class);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        PaymentEntity payment1 = mock(PaymentEntity.class);
        PaymentEntity payment2 = mock(PaymentEntity.class);

        when(trackerEntityManager.getResultsForCurrentUser(eq(PaymentEntity.class), eq("payment"), any(SimpleExpression.class))).thenReturn(Arrays.asList(payment1, payment2));

        sut.removePaymentsCategory(1, entityManager);

        verify(payment1).setCategory(null);
        verify(session).update(payment1);

        verify(payment2).setCategory(null);
        verify(session).update(payment2);
    }
}