package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.PaymentType;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
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

    @GET
    public List<PaymentEntity> getPayments() {
        return paymentDao.getUserPayments();
    }

    @POST
    public Response createPayment(
                                  @HeaderParam("amount") BigDecimal amount,
                                  @HeaderParam("type") PaymentType type,
                                  @HeaderParam("category") Long categoryId,
                                  @HeaderParam("note") String note
                                ) {
        validateInput(categoryId, note, amount, type);
        PaymentCategoryEntity category = categoryDao.findCategoryById(categoryId);

        paymentDao.createPayment(amount, type, category, note);

        return Response.ok().build();
    }

    private void validateInput(Long categoryId, String note, BigDecimal amount, PaymentType type) {
        // TODO
    }
}
