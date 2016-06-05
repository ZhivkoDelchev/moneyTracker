package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.ObjectFactory;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.exception.MoneyTrackerException;
import com.jako.moneytracker.exception.NotFoundException;
import com.jako.moneytracker.utils.test.DependencyResolver;
import com.jako.moneytracker.utils.test.TestOnStrings;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.security.Principal;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(Theories.class)
public class CategoryControllerTest {

    private CategoryController sut;

    @Mock private UserDao userDao;
    @Mock private CategoryDao categoryDao;
    @Mock private PaymentDao paymentDao;
    @Mock private ObjectFactory objectFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new DependencyResolver().resolveDependencies(new CategoryController(), Autowired.class, this);
    }

    @Test
    public void shouldGetCategoriesOfTheGivenUser() throws Exception {
        final String mail = "foo";

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(mail);

        final UserEntity userEntity = mock(UserEntity.class);
        when(userDao.findByEmail(mail)).thenReturn(userEntity);

        final Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "name").ignoreCase());

        final List<PaymentCategoryEntity> categories = mock(List.class);
        when(categoryDao.findByCreator(userEntity, sort)).thenReturn(categories);

        List<PaymentCategoryEntity> result = sut.getCategories(principal);

        assertSame(categories, result);
        verify(principal).getName();
        verify(userDao).findByEmail(mail);
        verify(categoryDao).findByCreator(userEntity, sort);
    }

    @Theory
    @Test(expected = MoneyTrackerException.class)
    public void shouldThrowExceptionIfNameContainsNonEnglishCharacters(@TestOnStrings({TestOnStrings.NULL, "", " ", "a1"}) String name) throws Exception {
        final Principal principal = mock(Principal.class);

        sut.createCategory(name, principal);
    }

    @Theory
    public void shouldCreateCategoryAndPersistIt(@TestOnStrings({"foo", "бар", "foo bar"}) final String name) throws Exception {
        final String email = "bar";
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        when(objectFactory.createPaymentCategoryEntity(name, user)).thenReturn(category);

        sut.createCategory(name, principal);

        verify(principal).getName();
        verify(userDao).findByEmail(email);
        verify(objectFactory).createPaymentCategoryEntity(name, user);
        verify(categoryDao).save(category);
    }

    @Test
    public void shouldTrimCategoryName() throws Exception {
        final String name = "foo ";
        final String email = "bar";
        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        when(objectFactory.createPaymentCategoryEntity(name.trim(), user)).thenReturn(category);

        sut.createCategory(name, principal);

        verify(principal).getName();
        verify(userDao).findByEmail(email);
        verify(objectFactory).createPaymentCategoryEntity(name.trim(), user);
        verify(categoryDao).save(category);
    }

    @Test
    public void shouldDeletePaymentsOfGivenCategory() throws Exception {
        final Long id = 1L;
        final String email = "foo";

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);

        when(categoryDao.findByIdAndCreator(id, user)).thenReturn(category);

        sut.deleteCategory(id, principal);

        verify(principal).getName();
        verify(userDao).findByEmail(email);
        verify(categoryDao).findByIdAndCreator(id, user);
        verify(paymentDao).removeCategory(category, user);
    }

    @Test
    public void shouldDeleteCategory() throws Exception {
        final Long id = 1L;
        final String email = "foo";

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        final PaymentCategoryEntity category = mock(PaymentCategoryEntity.class);
        when(categoryDao.findByIdAndCreator(id, user)).thenReturn(category);

        sut.deleteCategory(id, principal);

        verify(principal).getName();
        verify(userDao).findByEmail(email);
        verify(categoryDao).findByIdAndCreator(id, user);
        verify(categoryDao).delete(category);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionIfCategoryIsNotFound() throws Exception {
        final Long id = 1L;
        final String email = "foo";

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        final UserEntity user = mock(UserEntity.class);
        when(userDao.findByEmail(email)).thenReturn(user);

        when(categoryDao.findByIdAndCreator(id, user)).thenReturn(null);

        sut.deleteCategory(id, principal);
    }
}