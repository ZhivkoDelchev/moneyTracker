package com.jako.moneytracker.rest.valdator;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.exception.InvalidPaymentInputException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Component
public class PaymentValidator {

    private static final Pattern notePattern = Pattern.compile("^[a-zA-Z -//_]*$");

    public void validate(final PaymentCategoryEntity category, final String note, final BigDecimal amount, final PaymentType type, final Long paymentTimestamp) {
        validateCategoryId(category);
        validateNote(note);
        validateAmount(amount);
        validatePaymentType(type);
        validatePaymentDate(paymentTimestamp);
    }

    private void validateCategoryId(final PaymentCategoryEntity category) {
        if (category == null) {
            throw new InvalidPaymentInputException("Category");
        }
    }

    private void validateNote(final String note) {
        if (note != null && !notePattern.matcher(note).matches()) {
            throw new InvalidPaymentInputException("Note");
        }
    }

    private void validateAmount(final BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPaymentInputException("Negative amount");
        }
    }

    private void validatePaymentType(final PaymentType type) {
        if (type == null) {
            throw new InvalidPaymentInputException("Payment type");
        }
    }

    private void validatePaymentDate(final Long paymentTimestamp) {
        if (paymentTimestamp < 0) {
            throw new InvalidPaymentInputException("Date");
        }
    }
}
