package com.jako.moneytracker.dto;

import java.math.BigDecimal;

public class BalanceObject {

    public BigDecimal incomes;
    public BigDecimal expenses;
    public BigDecimal balance;

    public BalanceObject(final BigDecimal incomes, final BigDecimal expenses, final BigDecimal balance) {
        this.incomes = incomes;
        this.expenses = expenses;
        this.balance = balance;
    }
}
