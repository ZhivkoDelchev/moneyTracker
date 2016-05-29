package com.jako.moneytracker.rest.valdator;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.exception.InvalidPaymentInputException;
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

    @Test
    public void shouldThrowExceptionIfTimestampIsNegativeNumber() throws Exception {
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        expectedException.expect(InvalidPaymentInputException.class);
        expectedException.expectMessage("Cannot add payment. Date is invalid.");

        sut.validate(category, "note", new BigDecimal(1), PaymentType.DEPOSIT, -1L);
    }

    @Test
    public void shouldThrowExceptionIfAmountIsNegativeNumber() throws Exception {
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        expectedException.expect(InvalidPaymentInputException.class);
        expectedException.expectMessage("Cannot add payment. Negative amount is invalid.");

        sut.validate(category, "note", new BigDecimal(-1), PaymentType.INCOME, 1L);
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
    public void shouldPass() throws Exception {
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        sut.validate(category, "nosach dvigatel", new BigDecimal(1), PaymentType.EXPENSE, 1L);
    }
}