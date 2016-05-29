package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.*;
import com.jako.moneytracker.rest.valdator.PaymentValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/payments")
public class PaymentController {

    @Inject private UserDao userDao;
    @Inject private PaymentDao paymentDao;
    @Inject private CategoryDao categoryDao;
    @Inject private PaymentValidator paymentValidator;
    @Inject private ObjectFactory objectFactory;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    List<PaymentEntity> getPayments(final Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        return paymentDao.findByCreator(user, new PageRequest(0, 20, orderBy())).getContent();
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
