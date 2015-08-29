package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.exception.MoneyTrackerException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Jako on 10.8.2015 ;)
 */
@Stateless
@Path("/category")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoriesController {

    @Inject
    private CategoryDao categoryDao;
    @Inject
    private PaymentDao paymentDao;
    private Pattern namePattern = Pattern.compile("^[a-zA-Z]{1,255}$");

    @GET
    @Produces("application/json")
    public List<PaymentCategoryEntity> getCategories() {
        return categoryDao.getCategories();
    }

    @POST
    @Path("/{name}")
    public Response createCategory(@PathParam("name") String name) {
        validateCategoryName(name);
        categoryDao.createCategory(name);
        return Response.ok().build();
    }

    private void validateCategoryName(String name) {
        if (name == null || !namePattern.matcher(name).matches()) {
            throw new MoneyTrackerException("Invalid category name. Up to 255 characters from A to Z upper and lower case allowed.");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        paymentDao.removePaymentsCategory(id);
        categoryDao.deleteCategory(id);

        return Response.ok().build();
    }
}
