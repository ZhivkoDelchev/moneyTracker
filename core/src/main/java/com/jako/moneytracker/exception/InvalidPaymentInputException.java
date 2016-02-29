package com.jako.moneytracker.exception;

public class InvalidPaymentInputException extends MoneyTrackerException {
    private static final long serialVersionUID = 9099604506438509943L;

    public InvalidPaymentInputException(String field) {
        super("Cannot add payment. " + field + " is invalid.");
    }
}
