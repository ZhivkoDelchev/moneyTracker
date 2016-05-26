package com.jako.moneytracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends MoneyTrackerException {
    public NotFoundException(final String message) {
        super(message);
    }
}
