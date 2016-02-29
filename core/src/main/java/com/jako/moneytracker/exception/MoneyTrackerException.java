package com.jako.moneytracker.exception;

public class MoneyTrackerException extends RuntimeException {
    private static final long serialVersionUID = 2184023945190062719L;

    public MoneyTrackerException() {
        super();
    }

    public MoneyTrackerException(String message) {
        super(message);
    }

    public MoneyTrackerException(Throwable t) {
        super(t);
    }
}
