package com.jako.moneytracker.rest;

import com.jako.moneytracker.persistence.dao.CategoryDao;
import com.jako.moneytracker.persistence.dao.PaymentDao;
import com.jako.moneytracker.persistence.dao.UserDao;
import com.jako.moneytracker.persistence.entity.*;
import com.jako.moneytracker.exception.NotFoundException;
import com.jako.moneytracker.rest.valdator.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/payments")
public class PaymentController {

    @Autowired private UserDao userDao;
    @Autowired private PaymentDao paymentDao;
    @Autowired private CategoryDao categoryDao;
    @Autowired private PaymentValidator paymentValidator;
    @Autowired private ObjectFactory objectFactory;

    @RequestMapping(method = RequestMethod.GET, params = {"page", "size"})
    @ResponseBody
    List<PaymentEntity> getPayments(final Principal principal,
                                    final @RequestParam("page") int page,
                                    final @RequestParam("size") int size) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        final Page<PaymentEntity> paymentsPage = paymentDao.findByCreator(user, new PageRequest(page, size, orderBy()));
        if (page >= paymentsPage.getTotalPages()) {
            throw new NotFoundException("Page " + page + " not found.");
        }
        return paymentsPage.getContent();
    }

    private Sort orderBy() {
        return new Sort(Sort.Direction.DESC, "date").and(new Sort(Sort.Direction.DESC, "createdDate"));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    void createPayment(@RequestHeader("amount") BigDecimal amount,
                       @RequestHeader("type") PaymentType type,
                       @RequestHeader("category") Long categoryId,
                       @RequestHeader("note") String note,
                       @RequestHeader("paymentTimestamp") Long paymentTimestamp,
                       final Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        final PaymentCategoryEntity category = categoryDao.findByIdAndCreator(categoryId, user);
        paymentValidator.validate(category, note, amount, type, paymentTimestamp);
        final PaymentEntity payment = objectFactory.createPaymentEntity(amount, paymentTimestamp, note, category, type, user);
        paymentDao.save(payment);
    }
}
