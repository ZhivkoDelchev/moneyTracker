package com.jako.moneytracker.dto;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class BalanceObjectTest {

    @Test
    public void shouldSetIncomes() throws Exception {
        final BigDecimal incomes = mock(BigDecimal.class);

        final BalanceObject result = new BalanceObject(incomes, null, null);

        assertEquals(incomes, result.incomes);
    }

    @Test
    public void shouldSetIncomesToZeroIfParameterIsNull() throws Exception {
        final BalanceObject result = new BalanceObject(null, null, null);

        assertEquals(BigDecimal.ZERO, result.incomes);
    }

    @Test
    public void shouldSetExpenses() throws Exception {
        final BigDecimal expenses = mock(BigDecimal.class);

        final BalanceObject result = new BalanceObject(null, expenses, null);

        assertEquals(expenses, result.expenses);
    }

    @Test
    public void shouldReturnBalanceObjectWithZeroTotalExpensesIfNoWareFoundForGivenUser() throws Exception {
        final BalanceObject result = new BalanceObject(null, null, null);

        assertEquals(BigDecimal.ZERO, result.expenses);
    }

    @Test
    public void shouldSetBalanceToZeroIfIncomesExpensesAndInitialBalanceAreNull() throws Exception {
        final BalanceObject result = new BalanceObject(null, null, null);

        assertEquals(BigDecimal.ZERO, result.balance);
    }

    @Test
    public void shouldAddIncomesAndInitialBalanceAndSubtractedExpensesFromBalance() throws Exception {
        final BigDecimal expenses = new BigDecimal(90);
        final BigDecimal incomes = new BigDecimal(100);
        final BigDecimal initialAmount = new BigDecimal(1);

        final BalanceObject result = new BalanceObject(incomes, expenses, initialAmount);

        assertEquals(new BigDecimal(11), result.balance);
    }
}