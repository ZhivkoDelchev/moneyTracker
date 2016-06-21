package com.jako.moneytracker.rest;

import com.jako.moneytracker.dto.BalanceObject;
import com.jako.moneytracker.persistence.dao.AccountDao;
import com.jako.moneytracker.persistence.dao.PaymentDao;
import com.jako.moneytracker.persistence.dao.UserDao;
import com.jako.moneytracker.persistence.entity.PaymentType;
import com.jako.moneytracker.persistence.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BalanceControllerTest {

    private BalanceController sut;

    @Mock private AccountDao accountDao;
    @Mock private PaymentDao paymentDao;
    @Mock private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new BalanceController(accountDao, paymentDao, userDao);
    }

    @Test
    public void shouldReturnBalanceObjectWithTotalIncomesForGivenUser() throws Exception {
        final String email = "foo";
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final BigDecimal incomes = mock(BigDecimal.class);
        when(paymentDao.getSumAmountByCreatorAndType(user, PaymentType.INCOME)).thenReturn(incomes);

        final BalanceObject result = sut.getBalance(principal);

        assertEquals(incomes, result.incomes);
        verify(paymentDao).getSumAmountByCreatorAndType(user, PaymentType.INCOME);
        verify(userDao).findByEmail(email);
        verify(principal).getName();
    }

    @Test
    public void shouldReturnBalanceObjectWithZeroTotalIncomesIfNoIncomesWareFoundForTheGivenUser() throws Exception {
        final String email = "foo";
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final BalanceObject result = sut.getBalance(principal);

        assertEquals(BigDecimal.ZERO, result.incomes);
        verify(paymentDao).getSumAmountByCreatorAndType(user, PaymentType.INCOME);
        verify(userDao).findByEmail(email);
        verify(principal).getName();
    }

    @Test
    public void shouldReturnBalanceObjectWithTotalExpensesForGivenUser() throws Exception {
        final String email = "foo";
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final BigDecimal expenses = mock(BigDecimal.class);
        when(paymentDao.getSumAmountByCreatorAndType(user, PaymentType.EXPENSE)).thenReturn(expenses);

        final BalanceObject result = sut.getBalance(principal);

        assertEquals(expenses, result.expenses);
        verify(paymentDao).getSumAmountByCreatorAndType(user, PaymentType.EXPENSE);
        verify(userDao).findByEmail(email);
        verify(principal).getName();
    }

    @Test
    public void shouldReturnBalanceObjectWithZeroTotalExpensesIfNoWareFoundForGivenUser() throws Exception {
        final String email = "foo";
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final BalanceObject result = sut.getBalance(principal);

        assertEquals(BigDecimal.ZERO, result.expenses);
        verify(paymentDao).getSumAmountByCreatorAndType(user, PaymentType.EXPENSE);
        verify(userDao).findByEmail(email);
        verify(principal).getName();
    }

    @Test
    public void shouldReturnBalanceObjectWithZeroBalanceIfNoExpenseNoIncomesAndNoInitialBalanceWareFound() throws Exception {
        final String email = "foo";
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final BalanceObject result = sut.getBalance(principal);

        assertEquals(BigDecimal.ZERO, result.balance);
    }

    @Test
    public void shouldReturnBalanceWithAddedIncomesAndInitialBalanceAndSubtractedExpenses() throws Exception {
        final String email = "foo";
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final BigDecimal expenses = new BigDecimal(90);
        final BigDecimal incomes = new BigDecimal(100);
        when(paymentDao.getSumAmountByCreatorAndType(user, PaymentType.EXPENSE)).thenReturn(expenses);
        when(paymentDao.getSumAmountByCreatorAndType(user, PaymentType.INCOME)).thenReturn(incomes);

        final BigDecimal initialAmount = new BigDecimal(1);
        when(accountDao.getInitialAmountByCreator(user)).thenReturn(initialAmount);

        final BalanceObject result = sut.getBalance(principal);

        assertEquals(new BigDecimal(11), result.balance);
        verify(paymentDao).getSumAmountByCreatorAndType(user, PaymentType.EXPENSE);
        verify(paymentDao).getSumAmountByCreatorAndType(user, PaymentType.INCOME);
        verify(accountDao).getInitialAmountByCreator(user);
        verify(userDao).findByEmail(email);
        verify(principal).getName();
    }
}