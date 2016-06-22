package com.jako.moneytracker.rest.valdator;

import com.jako.moneytracker.persistence.entity.PaymentCategoryEntity;
import com.jako.moneytracker.persistence.entity.PaymentType;
import com.jako.moneytracker.exception.InvalidPaymentInputException;
import com.jako.moneytracker.utils.test.TestOnInts;
import com.jako.moneytracker.utils.test.TestOnLongs;
import com.jako.moneytracker.utils.test.TestOnStrings;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

@RunWith(Theories.class)
public class PaymentValidatorTest {

    private PaymentValidator sut;

    @Rule public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        sut = new PaymentValidator();
    }

    @Theory
    public void shouldThrowExceptionIfTimestampIsNegativeNumber(@TestOnLongs({-1L, -5L, -1000L, -500000L}) final long paymentTimestamp) throws Exception {
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        expectedException.expect(InvalidPaymentInputException.class);
        expectedException.expectMessage("Cannot add payment. Date is invalid.");

        sut.validate(category, "note", new BigDecimal(1), PaymentType.DEPOSIT, paymentTimestamp);
    }

    @Theory
    public void shouldThrowExceptionIfAmountIsNegativeNumber(@TestOnInts({-1, -2, -3, -100, -1000}) final int amountValue) throws Exception {
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        expectedException.expect(InvalidPaymentInputException.class);
        expectedException.expectMessage("Cannot add payment. Negative amount is invalid.");

        sut.validate(category, "note", new BigDecimal(amountValue), PaymentType.INCOME, 1L);
    }

    @Test
    public void shouldThrowExceptionIfCategoryCannotBeFound() throws Exception {
        expectedException.expect(InvalidPaymentInputException.class);
        expectedException.expectMessage("Cannot add payment. Category is invalid.");

        sut.validate(null, "note", new BigDecimal(1), PaymentType.DEPOSIT, 1L);
    }

    @Test
    public void shouldThrowExceptionIfPaymentTypeIsNull() throws Exception {
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        expectedException.expect(InvalidPaymentInputException.class);
        expectedException.expectMessage("Cannot add payment. Payment type is invalid.");

        sut.validate(category, "note", new BigDecimal(1), null, 1L);
    }

    @Theory
    public void shouldThrowExceptionIfNoteContainsNonLatinNonNumericCharacter(@TestOnStrings({"1ц", "qш", "@", "^"}) String note) throws Exception {
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        expectedException.expect(InvalidPaymentInputException.class);
        expectedException.expectMessage("Cannot add payment. Note is invalid.");

        sut.validate(category, note, new BigDecimal(1), PaymentType.EXPENSE, 1L);
    }

    @Theory
    public void shouldPass(@TestOnInts({0, 1, 100, 5000}) final int amountValue, @TestOnLongs({0L, 1L, 5L, 1000L, 5000L}) final long timestamp) throws Exception {
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        sut.validate(category, "nosach dvigatel", new BigDecimal(amountValue), PaymentType.EXPENSE, timestamp);
    }
}