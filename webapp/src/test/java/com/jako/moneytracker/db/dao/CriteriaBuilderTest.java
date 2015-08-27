package com.jako.moneytracker.db.dao;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Jako on 27.8.2015 ;)
 */
public class CriteriaBuilderTest {

    private CriteriaBuilder sut;

    @Before
    public void setUp() throws Exception {
        sut = new CriteriaBuilder();
    }

    @Test
    public void shouldReturnNonNullEqualsCriteria() throws Exception {
        assertNotNull(sut.buildEqualsCriteria("name", "value"));
    }
}