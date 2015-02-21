package com.jako.moneytracker.db.entity;

/**
 * Created by Jako on 15.2.2015 г..
 */
public enum PaymentType {

    INCOME("Income"),
    EXPENSE("Expense"),
    DEPOSIT("Deposit");

    private String name;

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
