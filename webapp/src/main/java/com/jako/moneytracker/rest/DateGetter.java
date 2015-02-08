package com.jako.moneytracker.rest;

import javax.enterprise.context.Dependent;
import java.util.Date;

/**
 * Created by Jako on 15.1.2015 г..
 */
@Dependent
public class DateGetter {

    public String getDate() {
        return new Date().toString();
    }
}
