package com.jako.moneytracker.rest;

import com.jako.moneytracker.exception.MyException;

import javax.enterprise.context.Dependent;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Jako on 7.4.2015.
 */
@Provider
@Dependent
public class CustomExceptionMapper implements ExceptionMapper<MyException> {

    @Override
    public Response toResponse(MyException exception) {
        return Response.status(Response.Status.FORBIDDEN).entity("test error").build();
    }
}
