package com.jako.moneytracker.db.entity;

import org.springframework.stereotype.Component;

@Component
public class ObjectFactory {

    public PaymentCategoryEntity createPaymentCategoryEntity(final String name, final UserEntity user ) {
        final PaymentCategoryEntity category = new PaymentCategoryEntity();
        category.setName(name);
        category.setCreator(user);
        return category;
    }
}
