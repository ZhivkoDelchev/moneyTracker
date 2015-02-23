package com.jako.moneytracker;

import com.jako.moneytracker.rest.PaymentsController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jako on 14.1.2015 г..
 */
@ApplicationPath("/rest/")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(PaymentsController.class));
    }
}
