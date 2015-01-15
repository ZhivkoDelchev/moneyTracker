package com.jako.moneytracker.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateGetterTest {

    private DateGetter sut;

    @Before
    public void setUp() throws Exception {
        this.sut = new DateGetter();
    }

    @Test
    public void shouldReturnCurrentDate() throws Exception {
        assertEquals(new Date().toString(), sut.getDate());
    }
}