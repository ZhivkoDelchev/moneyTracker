package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.test.utils.DependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Jako on 27.8.2015 ;)
 */
public class PaymentsControllerTest {

    private PaymentsController sut;
    @Mock private PaymentDao paymentDao;
    @Mock private CategoryDao categoryDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new DependencyResolver().resolveDependencies(new PaymentsController(), this);
    }

    @Test
    public void shouldReturnAllUserPayments() throws Exception {
        List<PaymentEntity> payments = mock(List.class);

        when(paymentDao.getUserPayments()).thenReturn(payments);

        List<PaymentEntity> result = sut.getPayments();

        assertEquals(payments, result);
    }

    @Test
    public void shouldCreatePaymentWithCorrespondingInput() throws Exception {
        String note = "note";
        long categoryId = 1;
        BigDecimal amount = mock(BigDecimal.class);
        PaymentType paymentType = PaymentType.DEPOSIT;
        long timestamp = System.currentTimeMillis();

        PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        when(categoryDao.findCategoryById(categoryId)).thenReturn(category);

        sut.createPayment(amount, paymentType, categoryId, note, timestamp);

        verify(paymentDao).createPayment(amount, paymentType, category, note, new Date(timestamp));
    }
}