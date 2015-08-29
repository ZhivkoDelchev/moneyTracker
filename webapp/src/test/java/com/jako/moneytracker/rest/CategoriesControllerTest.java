package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.exception.MoneyTrackerException;
import com.jako.moneytracker.test.utils.DependencyResolver;
import com.jako.moneytracker.test.utils.TestOn;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Jako on 28.8.2015 ;)
 */
@RunWith(Theories.class)
public class CategoriesControllerTest {

    private CategoriesController sut;

    @Mock private CategoryDao categoryDao;
    @Mock private PaymentDao paymentDao;
    @Rule public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new DependencyResolver().resolveDependencies(new CategoriesController(), this);
    }

    @Theory
    public void shouldListAllCategories() throws Exception {
        @SuppressWarnings("unchecked")
        List<PaymentCategoryEntity> categories = mock(List.class);
        when(categoryDao.getCategories()).thenReturn(categories);

        List<PaymentCategoryEntity> result = sut.getCategories();
        assertSame(categories, result);
    }

    @Theory
    public void shouldListCategoriesWithHttpGetMethod() throws Exception {
        Method getCategories = CategoriesController.class.getMethod("getCategories");

        assertTrue(getCategories.isAnnotationPresent(GET.class));
    }

    @Theory
    public void shouldProduceJsonWhenAskedForAllCategories() throws Exception {
        Method getCategories = CategoriesController.class.getMethod("getCategories");

        assertTrue(getCategories.isAnnotationPresent(Produces.class));
        Produces produceAnnotation = getCategories.getAnnotation(Produces.class);

        String[] values = produceAnnotation.value();
        assertEquals(1, values.length);
        assertEquals("application/json", values[0]);
    }

    @Theory
    public void shouldCreateCategory() throws Exception {
        String categoryName = "category";

        sut.createCategory(categoryName);

        verify(categoryDao).createCategory(categoryName);
    }

    @Theory
    public void shouldReturnHttpResponseCode200WhenCreatingCategory() throws Exception {
        String categoryName = "category";

        Response result = sut.createCategory(categoryName);

        assertEquals(200, result.getStatus());
    }

    @Theory
    public void shouldCreateCategoriesWithHttpPostMethod() throws Exception {
        Method getCategories = CategoriesController.class.getMethod("createCategory", String.class);

        assertTrue(getCategories.isAnnotationPresent(POST.class));
    }

    @Theory
    public void shouldCreateCategoryWithPathParameterName() throws Exception {
        Method getCategories = CategoriesController.class.getMethod("createCategory", String.class);

        assertTrue(getCategories.isAnnotationPresent(Path.class));
        Path annotation = getCategories.getAnnotation(Path.class);

        assertEquals("/{name}", annotation.value());
    }

    @Theory
    public void shouldThrowExceptionIfNameContainsNonEnglishCharacters(@TestOn(strings={"", " ", " a", "a1", "фa"}) String name) throws Exception {
        expected.expect(MoneyTrackerException.class);
        expected.expectMessage("Invalid category name. Up to 255 characters from A to Z upper and lower case allowed.");
        sut.createCategory(name);
    }
}