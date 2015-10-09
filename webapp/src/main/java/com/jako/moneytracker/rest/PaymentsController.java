package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.rest.validator.PaymentValidator;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Jako on 21.2.2015 .
 */
@Stateless
@Path("/payments")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PaymentsController {

    @Inject
    private PaymentDao paymentDao;
    @Inject
    private CategoryDao categoryDao;
    @Inject
    private PaymentValidator paymentValidator;

    @GET
    @Produces("application/json")
    public List<PaymentEntity> getPayments() {
        return paymentDao.getUserPayments();
    }

    @POST
    public Response createPayment(
                                  @HeaderParam("amount") BigDecimal amount,
                                  @HeaderParam("type") PaymentType type,
                                  @HeaderParam("category") Long categoryId,
                                  @HeaderParam("note") String note,
                                  @HeaderParam("paymentTimestamp") Long paymentTimestamp
                                ) {
        paymentValidator.validate(categoryId, note, amount, type, paymentTimestamp);
        Date date = new Date(paymentTimestamp);
        PaymentCategoryEntity category = categoryDao.findCategoryById(categoryId);

        paymentDao.createPayment(amount, type, category, note, date);

        return Response.ok().build();
    }
}
