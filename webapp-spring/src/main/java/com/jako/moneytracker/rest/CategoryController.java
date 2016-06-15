package com.jako.moneytracker.rest;

import com.jako.moneytracker.persistence.dao.CategoryDao;
import com.jako.moneytracker.persistence.dao.PaymentDao;
import com.jako.moneytracker.persistence.dao.UserDao;
import com.jako.moneytracker.persistence.entity.ObjectFactory;
import com.jako.moneytracker.persistence.entity.PaymentCategoryEntity;
import com.jako.moneytracker.persistence.entity.UserEntity;
import com.jako.moneytracker.exception.MoneyTrackerException;
import com.jako.moneytracker.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/rest/categories")
public class CategoryController {

    @Autowired private CategoryDao categoryDao;
    @Autowired private UserDao userDao;
    @Autowired private PaymentDao paymentDao;
    @Autowired private ObjectFactory objectFactory;

    private Pattern namePattern = Pattern.compile("^[a-zA-Zа-яА-Я ]{1,255}$");

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<PaymentCategoryEntity> getCategories(final Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        return categoryDao.findByCreator(user, getSort());
    }

    private Sort getSort() {
        return new Sort(new Sort.Order(Sort.Direction.ASC, "name").ignoreCase());
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void createCategory(final @PathVariable("name") String name, final Principal principal) {
        validateCategoryName(name);
        final UserEntity user = userDao.findByEmail(principal.getName());
        final PaymentCategoryEntity category = objectFactory.createPaymentCategoryEntity(name.trim(), user);
        categoryDao.save(category);
    }

    private void validateCategoryName(String name) {
        if (name == null || !namePattern.matcher(name.trim()).matches()) {
            throw new MoneyTrackerException("Invalid category name. Up to 255 characters from A to Z upper and lower case allowed.");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCategory(final @PathVariable("id") Long id, final Principal principal) {
        final UserEntity user = userDao.findByEmail(principal.getName());
        final PaymentCategoryEntity category = categoryDao.findByIdAndCreator(id, user);
        if (category == null) {
            throw new NotFoundException("Category with id " + id + " not found!");
        }
        paymentDao.removeCategory(category, user);
        categoryDao.delete(category);
    }
}
