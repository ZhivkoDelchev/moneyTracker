package com.jako.moneytracker.rest;

import com.jako.moneytracker.exception.MoneyTrackerException;

import javax.enterprise.context.Dependent;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Dependent
public class CustomExceptionMapper implements ExceptionMapper<MoneyTrackerException> {

    @Override
    public Response toResponse(MoneyTrackerException exception) {
        return Response.status(Response.Status.FORBIDDEN).entity("test error").build();
    }
}
