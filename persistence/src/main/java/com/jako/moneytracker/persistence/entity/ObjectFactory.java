package com.jako.moneytracker.persistence.entity;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class ObjectFactory {

    public PaymentCategoryEntity createPaymentCategoryEntity(final String name, final UserEntity user ) {
        final PaymentCategoryEntity category = new PaymentCategoryEntity();
        category.setName(name);
        category.setCreator(user);
        return category;
    }

    public PaymentEntity createPaymentEntity(final BigDecimal amount,
                                             final Long paymentTimeStamp,
                                             final String note,
                                             final PaymentCategoryEntity category,
                                             final PaymentType type,
                                             final UserEntity user) {
        final PaymentEntity payment = new PaymentEntity();
        payment.setAmount(amount);
        payment.setDate(new Date(paymentTimeStamp));
        payment.setNote(note);
        payment.setCategory(category);
        payment.setPaymentType(type);
        payment.setCreator(user);
        return payment;
    }
}
