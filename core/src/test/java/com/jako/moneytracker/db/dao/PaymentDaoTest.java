package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

        final Order dateOrder = mock(Order.class);
        when(criteriaBuilder.descending("date")).thenReturn(dateOrder);

        final Order createdDateOrder = mock(Order.class);
        when(criteriaBuilder.descending("createdDate")).thenReturn(createdDateOrder);

        final Order[] orders = {dateOrder, createdDateOrder};

        when(trackerEntityManager.getResultsForCurrentUser(PaymentEntity.class, "payment", 20, orders, null)).thenReturn(payments);

        List<PaymentEntity> result = sut.getUserPayments();

        Assert.assertEquals(payments, result);
        verify(trackerEntityManager).getResultsForCurrentUser(PaymentEntity.class, "payment", 20, orders, null);
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
    public void shouldCreatePayment() throws Exception {
        PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        BigDecimal amount = mock(BigDecimal.class);
        Date date = new Date();

        sut.createPayment(amount, PaymentType.DEPOSIT, category, "note", date);
        // TODO: verify if proper category is persisted.
        verify(trackerEntityManager).persist(Matchers.any(PaymentEntity.class));
    }
}