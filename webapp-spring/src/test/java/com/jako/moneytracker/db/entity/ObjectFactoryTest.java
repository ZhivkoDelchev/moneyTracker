package com.jako.moneytracker.db.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
}