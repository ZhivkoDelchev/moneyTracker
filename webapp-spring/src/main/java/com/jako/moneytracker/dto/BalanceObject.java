package com.jako.moneytracker.dto;

import java.math.BigDecimal;

public class BalanceObject {

    public BigDecimal incomes;
    public BigDecimal expenses;
    public BigDecimal balance;

    public BalanceObject(final BigDecimal incomes, final BigDecimal expenses, final BigDecimal initialAmount) {
        BigDecimal balance = BigDecimal.ZERO;
        if (incomes != null) {
            this.incomes = incomes;
            balance = balance.add(incomes);
        } else {
            this.incomes = BigDecimal.ZERO;
        }
        if (expenses != null) {
            this.expenses = expenses;
            balance = balance.subtract(expenses);
        } else {
            this.expenses = BigDecimal.ZERO;
        }
        if (initialAmount != null) {
            balance = balance.add(initialAmount);
        }
        this.balance = balance;
    }
}
