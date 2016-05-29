package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/payments")
public class PaymentController {

    @Inject private UserDao userDao;
    @Inject private PaymentDao paymentDao;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    List<PaymentEntity> getPayments(final Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        return paymentDao.findByCreator(user, new PageRequest(1, 20)).getContent();
    }
}
