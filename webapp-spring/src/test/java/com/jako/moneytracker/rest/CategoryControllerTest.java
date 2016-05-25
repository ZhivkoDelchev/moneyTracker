package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.utils.test.DependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryControllerTest {

    private CategoryController sut;

    @Mock private UserDao userDao;
    @Mock private CategoryDao categoryDao;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new DependencyResolver().resolveDependencies(new CategoryController(), Inject.class, this);
    }

    @Test
    public void shouldGetCategoriesOfTheGivenUser() throws Exception {
        final String mail = "foo";

        final Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(mail);

        final UserEntity userEntity = mock(UserEntity.class);
        when(userDao.findByEmail(mail)).thenReturn(userEntity);

        final List<PaymentCategoryEntity> categories = mock(List.class);
        when(categoryDao.findByCreator(userEntity)).thenReturn(categories);

        List<PaymentCategoryEntity> result = sut.getCategories(principal);

        assertSame(categories, result);
        verify(principal).getName();
        verify(userDao).findByEmail(mail);
        verify(categoryDao).findByCreator(userEntity);
    }
}