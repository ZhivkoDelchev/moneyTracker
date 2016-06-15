package com.jako.moneytracker.rest;

import com.jako.moneytracker.persistence.dao.AccountDao;
import com.jako.moneytracker.persistence.dao.PaymentDao;
import com.jako.moneytracker.persistence.dao.UserDao;
import com.jako.moneytracker.persistence.entity.PaymentType;
import com.jako.moneytracker.persistence.entity.UserEntity;
import com.jako.moneytracker.dto.BalanceObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/rest/balance")
public class BalanceController {

    @Autowired private PaymentDao paymentDao;
    @Autowired private UserDao userDao;
    @Autowired private AccountDao accountDao;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    BalanceObject getBalance(final Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        final BigDecimal incomes = paymentDao.getSumAmountByCreatorAndType(user, PaymentType.INCOME);
        final BigDecimal expenses = paymentDao.getSumAmountByCreatorAndType(user, PaymentType.EXPENSE);
        final BigDecimal initialAmount = accountDao.getInitialAmountByCreator(user);
        return new BalanceObject(incomes, expenses, initialAmount.add(incomes).subtract(expenses));
    }
}
