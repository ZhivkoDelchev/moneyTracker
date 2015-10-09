package com.jako.moneytracker.exception;

/**
 * Created by Jako on 9.10.2015 ;)
 */
public class InvalidPaymentInputException extends MoneyTrackerException {
    private static final long serialVersionUID = 9099604506438509943L;

    public InvalidPaymentInputException(String field) {
        super("Cannot add payment. " + field + " is invalid.");
    }
}
