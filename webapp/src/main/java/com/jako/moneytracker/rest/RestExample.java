package com.jako.moneytracker.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by Jako on 14.1.2015 г..
 */
@Stateless
@Path("/pojo")
public class RestExample {

    private DateGetter dateGetter;

    @Inject
    public RestExample(final DateGetter dateGetter) {
        this.dateGetter = dateGetter;
    }

    @GET
    public String get() {
        return "pojo ok @ " + dateGetter.getDate();
    }
}
