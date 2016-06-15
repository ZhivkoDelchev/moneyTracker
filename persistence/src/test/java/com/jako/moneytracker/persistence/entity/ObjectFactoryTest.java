package com.jako.moneytracker.persistence.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;

public class ObjectFactoryTest {

    private ObjectFactory sut;

    @Before
    public void setUp() throws Exception {
        sut = new ObjectFactory();
    }

    @Test
    public void shouldCreateCategoryWithAGivenName() throws Exception {
        final String name = "foo";
        final UserEntity user = Mockito.mock(UserEntity.class);

        final PaymentCategoryEntity paymentCategoryEntity = sut.createPaymentCategoryEntity(name, user);

        Assert.assertEquals(name, paymentCategoryEntity.getName());
    }

    @Test
    public void shouldCreatePayment() throws Exception {
        final BigDecimal amount = BigDecimal.ONE;
        final Long paymentTimestamp = 2L;
        final String note = "foo";
        final PaymentCategoryEntity category = Mockito.mock(PaymentCategoryEntity.class);
        final PaymentType type = PaymentType.EXPENSE;
        final UserEntity user = Mockito.mock(UserEntity.class);

        final PaymentEntity paymentEntity = sut.createPaymentEntity(amount, paymentTimestamp, note, category, type, user);

        Assert.assertNotNull(paymentEntity);
        Assert.assertEquals(amount, paymentEntity.getAmount());
        Assert.assertEquals(new Date(paymentTimestamp), paymentEntity.getDate());
        Assert.assertEquals(note, paymentEntity.getNote());
        Assert.assertEquals(category, paymentEntity.getCategory());
        Assert.assertEquals(type, paymentEntity.getPaymentType());
        Assert.assertEquals(user, paymentEntity.getCreator());
    }
}