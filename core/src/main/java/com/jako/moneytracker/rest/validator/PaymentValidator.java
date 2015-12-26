package com.jako.moneytracker.rest.validator;

import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.exception.InvalidPaymentInputException;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Created by Jako on 9.10.2015 ;)
 */
@Dependent
@Default
public class PaymentValidator {

    private static final Pattern notePattern = Pattern.compile("^[a-zA-Z]*$");

    public void validate(Long categoryId, String note, BigDecimal amount, PaymentType type, Long paymentTimestamp) {
        validateCategoryId(categoryId);
        validateNote(note);
        validateAmount(amount);
        validatePaymentType(type);
        validatePaymentDate(paymentTimestamp);
    }

    private void validateCategoryId(Long categoryId) {
        if (categoryId < 0) {
            throw new InvalidPaymentInputException("Category");
        }
    }

    private void validateNote(String note) {
        if (note != null && !notePattern.matcher(note).matches()) {
            throw new InvalidPaymentInputException("Note");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPaymentInputException("Negative amount");
        }
    }

    private void validatePaymentType(PaymentType type) {
        if (type == null) {
            throw new InvalidPaymentInputException("Payment type");
        }
    }

    private void validatePaymentDate(Long paymentTimestamp) {
        if (paymentTimestamp < 0) {
            throw new InvalidPaymentInputException("Date");
        }
    }
}
