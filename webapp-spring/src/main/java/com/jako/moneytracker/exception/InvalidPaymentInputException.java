package com.jako.moneytracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPaymentInputException extends MoneyTrackerException {
    public InvalidPaymentInputException(String field) {
        super("Cannot add payment. " + field + " is invalid.");
    }
}
