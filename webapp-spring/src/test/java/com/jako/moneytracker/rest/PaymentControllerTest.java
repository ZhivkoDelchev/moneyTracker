package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.*;
import com.jako.moneytracker.rest.valdator.PaymentValidator;
import com.jako.moneytracker.utils.test.DependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    private PaymentController sut;

    @Mock private UserDao userDao;
    @Mock private PaymentDao paymentDao;
    @Mock private CategoryDao categoryDao;
    @Mock private PaymentValidator paymentValidator;
    @Mock private ObjectFactory objectFacory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new DependencyResolver().resolveDependencies(new PaymentController(), Autowired.class, this);
    }

    @Test
    public void shouldReturnFirst20PaymentsForGivenUser() throws Exception {
        final String email = "foo";
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final Sort sort = new Sort(Sort.Direction.DESC, "date").and(new Sort(Sort.Direction.DESC, "createdDate"));
        final PageRequest pageRequest = new PageRequest(0, 20, sort);

        final List<PaymentEntity> payments = mock(List.class);

        final Page<PaymentEntity> pageOfPayments = mock(Page.class);
        when(pageOfPayments.getContent()).thenReturn(payments);

        when(paymentDao.findByCreator(user, pageRequest)).thenReturn(pageOfPayments);

        final List<PaymentEntity> result = sut.getPayments(principal);

        verify(principal).getName();
        verify(userDao).findByEmail(email);
        verify(paymentDao).findByCreator(user, pageRequest);
        assertSame(payments, result);
    }

    @Test
    public void shouldValidateUserInput() throws Exception {
        final BigDecimal amount = BigDecimal.ONE;
        final Long categoryId = 1L;
        final String note = "foo";
        final Long paymentTimeStamp = 2L;
        final String email = "bar";
        final PaymentType paymentType = PaymentType.EXPENSE;

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        when(categoryDao.findByIdAndCreator(categoryId, user)).thenReturn(category);

        sut.createPayment(amount, paymentType, categoryId, note, paymentTimeStamp, principal);

        verify(principal).getName();
        verify(userDao).findByEmail(email);
        verify(categoryDao).findByIdAndCreator(categoryId, user);
        verify(paymentValidator).validate(category, note, amount, paymentType, paymentTimeStamp);
    }

    @Test
    public void shouldCreateAndPersistPaymentWithUserInput() throws Exception {
        final BigDecimal amount = BigDecimal.ONE;
        final Long categoryId = 1L;
        final String note = "foo";
        final Long paymentTimeStamp = 2L;
        final String email = "bar";
        final PaymentType type = PaymentType.EXPENSE;

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        when(categoryDao.findByIdAndCreator(categoryId, user)).thenReturn(category);

        final PaymentEntity payment = mock(PaymentEntity.class);
        when(objectFacory.createPaymentEntity(amount, paymentTimeStamp, note, category, type, user)).thenReturn(payment);

        sut.createPayment(amount, type, categoryId, note, paymentTimeStamp, principal);

        verify(objectFacory).createPaymentEntity(amount, paymentTimeStamp, note, category, type, user);
        verify(paymentDao).save(payment);
    }
}