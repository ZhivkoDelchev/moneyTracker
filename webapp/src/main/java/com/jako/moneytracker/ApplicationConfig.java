package com.jako.moneytracker;

import com.jako.moneytracker.rest.CategoriesController;
import com.jako.moneytracker.rest.CustomExceptionMapper;
import com.jako.moneytracker.rest.PaymentsController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jako on 14.1.2015.
 */
@ApplicationPath("/rest/")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(PaymentsController.class);
        classes.add(CategoriesController.class);

        classes.add(CustomExceptionMapper.class);
        return classes;
    }
}
