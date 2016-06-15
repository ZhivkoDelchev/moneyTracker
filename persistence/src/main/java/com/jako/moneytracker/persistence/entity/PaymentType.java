package com.jako.moneytracker.persistence.entity;

public enum PaymentType {

    INCOME("Income"),
    EXPENSE("Expense"),
    DEPOSIT("Deposit");

    private final String name;

    PaymentType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PaymentType{" +
                "name='" + name + '\'' +
                '}';
    }
}
