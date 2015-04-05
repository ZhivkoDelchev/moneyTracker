package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        List<PaymentCategoryEntity> categories = categoryDao.getCategories(userPrincipal.getName(), entityManager);
        return categories;
    }
}
