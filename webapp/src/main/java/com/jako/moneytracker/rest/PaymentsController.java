package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.PaymentDao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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

    @PersistenceContext(unitName = "tracker")
    private EntityManager entityManager;

    @GET
    public String get() {
        return paymentDao.getUserPayments(userPrincipal.getName(), entityManager).toString();
    }
}
