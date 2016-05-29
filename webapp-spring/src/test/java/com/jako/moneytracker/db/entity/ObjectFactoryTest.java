package com.jako.moneytracker.db.entity;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ObjectFactoryTest {

    private ObjectFactory sut;

    @Before
    public void setUp() throws Exception {
        sut = new ObjectFactory();
    }

    @Test
    public void shouldCreateCategoryWithAGivenName() throws Exception {
        final String name = "foo";
        final UserEntity user = mock(UserEntity.class);

        final PaymentCategoryEntity paymentCategoryEntity = sut.createPaymentCategoryEntity(name, user);

        assertEquals(name, paymentCategoryEntity.getName());
    }

    @Test
    public void shouldCreatePayment() throws Exception {
        final BigDecimal amount = BigDecimal.ONE;
        final Long paymentTimestamp = 2L;
        final String note = "foo";
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        final PaymentType type = PaymentType.EXPENSE;
        final UserEntity user = mock(UserEntity.class);

        final PaymentEntity paymentEntity = sut.createPaymentEntity(amount, paymentTimestamp, note, category, type, user);

        assertNotNull(paymentEntity);
        assertEquals(amount, paymentEntity.getAmount());
        assertEquals(new Date(paymentTimestamp), paymentEntity.getDate());
        assertEquals(note, paymentEntity.getNote());
        assertEquals(category, paymentEntity.getCategory());
        assertEquals(type, paymentEntity.getPaymentType());
        assertEquals(user, paymentEntity.getCreator());
    }
}