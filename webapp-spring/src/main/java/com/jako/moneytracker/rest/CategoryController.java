package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.dao.CategoryDao;
import com.jako.moneytracker.db.dao.PaymentDao;
import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.ObjectFactory;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.exception.MoneyTrackerException;
import com.jako.moneytracker.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/rest/categories")
public class CategoryController {

    @Inject private CategoryDao categoryDao;
    @Inject private UserDao userDao;
    @Inject private PaymentDao paymentDao;
    @Inject private ObjectFactory objectFactory;

    private Pattern namePattern = Pattern.compile("^[a-zA-Z]{1,255}$");

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<PaymentCategoryEntity> getCategories(Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        return categoryDao.findByCreator(user);
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void createCategory(@PathVariable("name") String name, Principal principal) {
        validateCategoryName(name);
        final UserEntity user = userDao.findByEmail(principal.getName());
        final PaymentCategoryEntity category = objectFactory.createPaymentCategoryEntity(name, user);
        categoryDao.save(category);
    }

    private void validateCategoryName(String name) {
        if (name == null || !namePattern.matcher(name).matches()) {
            throw new MoneyTrackerException("Invalid category name. Up to 255 characters from A to Z upper and lower case allowed.");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCategory(@PathVariable("id") Long id, final Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        final PaymentCategoryEntity category = categoryDao.findByIdAndCreator(id, user);
        if (category == null) {
            throw new NotFoundException("Category with id " + id + " not found!");
        }
        paymentDao.removeCategory(category, user);
        categoryDao.delete(category);
    }

}
