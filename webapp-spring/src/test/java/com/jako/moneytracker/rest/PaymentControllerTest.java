package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.utils.test.DependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    private PaymentController sut;

    @Mock private UserDao userDao;
    @Mock private PaymentDao paymentDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new DependencyResolver().resolveDependencies(new PaymentController(), Inject.class, this);
    }

    @Test
    public void shouldReturnFirst20PaymentsForGivenUser() throws Exception {
        final String email = "foo";
        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final PageRequest pageRequest = new PageRequest(1, 20);

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
}