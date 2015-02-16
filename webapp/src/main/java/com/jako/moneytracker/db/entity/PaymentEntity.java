package com.jako.moneytracker.db.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Jako on 15.2.2015 г..
 */
@Entity
public class PaymentEntity extends BaseEntity{

    @Column( scale = 2, precision = 12)
    private BigDecimal amount;
    @OneToOne
    @JoinColumn(name = "category_id")
    private PaymentCategoryEntity category;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(PaymentCategoryEntity category) {
        this.category = category;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
