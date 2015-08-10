package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Jako on 10.8.2015 ã. ;)
 */
@Stateless
@Path("/category")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoriesController {

    @Inject
    private CategoryDao categoryDao;
    @Inject
    private PaymentDao paymentDao;
    @Inject
    private UserDao userDao;


    @GET
    @Produces("application/json")
    public List<PaymentCategoryEntity> getCategory() {
        return categoryDao.getCategories();
    }

    @POST
    @Path("/{name}")
    public Response createCategory(@PathParam("name") String name) {
        UserEntity user = userDao.getUser();
        categoryDao.createCategory(name, user);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        paymentDao.removePaymentsCategory(id);
        categoryDao.deleteCategory(id);

        return Response.ok().build();
    }
}
