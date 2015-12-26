package com.jako.moneytracker.db.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertNotNull(sut.buildEqualsCriteria("name", "value"));
    }
}