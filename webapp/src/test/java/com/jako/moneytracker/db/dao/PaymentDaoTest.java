package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentDaoTest {

    private PaymentDao sut;
    @Mock private TrackerEntityManager trackerEntityManager;
    @Mock private CriteriaBuilder criteriaBuilder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new PaymentDao(trackerEntityManager, criteriaBuilder);
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
        long id = 1;

        PaymentEntity payment1 = mock(PaymentEntity.class);
        PaymentEntity payment2 = mock(PaymentEntity.class);

        SimpleExpression equalsCriteria = mock(SimpleExpression.class);
        when(criteriaBuilder.buildEqualsCriteria("payment.category.id", id)).thenReturn(equalsCriteria);

        when(trackerEntityManager.getResultsForCurrentUser(PaymentEntity.class, "payment", equalsCriteria)).thenReturn(Arrays.asList(payment1, payment2));

        sut.removePaymentsCategory(id);

        verify(payment1).setCategory(null);
        verify(trackerEntityManager).update(payment1);

        verify(payment2).setCategory(null);
        verify(trackerEntityManager).update(payment2);
    }

    @Test
    public void shouldCreateCategory() throws Exception {
        PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        BigDecimal amount = mock(BigDecimal.class);

        sut.createPayment("note", amount, category, PaymentType.DEPOSIT);
        // TODO: verify if proper category is persisted.
        verify(trackerEntityManager).persist(any(PaymentEntity.class));
    }
}