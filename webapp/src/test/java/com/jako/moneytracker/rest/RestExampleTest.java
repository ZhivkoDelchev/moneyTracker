package com.jako.moneytracker.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class RestExampleTest {

    @Mock
    private DateGetter dataGetter;

    private RestExample sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.sut = new RestExample(dataGetter);
    }

    @Test
    public void shouldPostfixPojoWithCurrentDate() throws Exception {
        String text = "foo";
        Mockito.when(dataGetter.getDate()).thenReturn(text);
        assertEquals("pojo ok @ " + text, sut.get() );
    }
}