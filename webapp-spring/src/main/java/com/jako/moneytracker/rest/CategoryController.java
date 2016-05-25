package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest/categories")
public class CategoryController {

    @Inject private CategoryDao categoryDao;
    @Inject private UserDao userDao;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<PaymentCategoryEntity> getCategories(Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        return categoryDao.findByCreator(user);
    }
}
