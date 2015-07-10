package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.UserEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Jako on 21.2.2015 г..
 */
@Stateless
@Path("/payments")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PaymentsController {

    @Inject
    private java.security.Principal userPrincipal;

    @Inject
    private PaymentDao paymentDao;
    @Inject
    private CategoryDao categoryDao;
    @Inject
    private UserDao userDao;

    @PersistenceContext(unitName = "tracker")
    private EntityManager entityManager;

    @GET
    public String get() {
        List<PaymentEntity> userPayments = paymentDao.getUserPayments(userPrincipal.getName(), entityManager);
        return userPayments.toString();
    }

    @GET
    @Path("/category")
    @Produces("application/json")
    public List<PaymentCategoryEntity> getCategory() {
        return categoryDao.getCategories();
    }

    @POST
    @Path("/category/{name}")
    public Response createCategory(@PathParam("name") String name) {
        UserEntity user = userDao.getUser();
        categoryDao.createCategory(name, user, entityManager);

        return Response.ok().build();
    }

    @DELETE
    @Path("/category/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        paymentDao.removePaymentsCategory(id, userPrincipal.getName(), entityManager);
        categoryDao.deleteCategory(id, userPrincipal.getName(), entityManager);

        return Response.ok().build();
    }
}
