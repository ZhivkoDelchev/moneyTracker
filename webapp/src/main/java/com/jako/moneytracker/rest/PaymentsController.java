package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.PaymentEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by Jako on 21.2.2015 г..
 */
@Stateless
@Path("/payments")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PaymentsController {

    @Inject
    private PaymentDao paymentDao;

    @Inject
    private UserDao userDao;

    @GET
    public List<PaymentEntity> get() {
        return paymentDao.getUserPayments();
    }
}
