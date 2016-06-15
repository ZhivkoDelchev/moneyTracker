package com.jako.moneytracker.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "account")
public class AccountEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "initial_amount", scale = 2, precision = 12)
    private BigDecimal initialAmount;

    private String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = name;
    }

    private BigDecimal getInitialAmount() {
        return initialAmount;
    }

    private void setInitialAmount(final BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }
}
