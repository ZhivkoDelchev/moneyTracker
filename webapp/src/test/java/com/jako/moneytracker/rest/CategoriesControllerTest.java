package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.test.utils.DependencyResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jako on 28.8.2015 ;)
 */
public class CategoriesControllerTest {

    private CategoriesController sut;

    @Mock private CategoryDao categoryDao;
    @Mock private PaymentDao paymentDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new DependencyResolver().resolveDependencies(new CategoriesController(), this);
    }

    @Test
    public void shouldListAllCategories() throws Exception {
        List<PaymentCategoryEntity> categories = mock(List.class);
        when(categoryDao.getCategories()).thenReturn(categories);

        List<PaymentCategoryEntity> result = sut.getCategories();
        assertSame(categories, result);
    }

    @Test
    public void shouldListCategoriesWithHttpGetMethod() throws Exception {
        Method getCategories = CategoriesController.class.getMethod("getCategories");

        assertTrue(getCategories.isAnnotationPresent(GET.class));
    }

    @Test
    public void shouldProduceJsonWhenAskedForAllCategories() throws Exception {
        Method getCategories = CategoriesController.class.getMethod("getCategories");

        Produces produceAnnotation = getCategories.getAnnotation(Produces.class);

        assertNotNull(produceAnnotation);
        String[] values = produceAnnotation.value();
        assertEquals(1, values.length);
        assertEquals("application/json", values[0]);
    }
}