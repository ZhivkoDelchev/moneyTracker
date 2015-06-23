package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Jako on 13.4.2015 �..
 */
public class UserDaoTest {

    private UserDao sut;
    @Mock private TrackerEntityManager trackerEntityManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new UserDao(trackerEntityManager);
    }

    @Test
    public void testQueryUserByEmailAndReturnSingleUniqueResult() throws Exception {
        sut.getUser();

        verify(trackerEntityManager).getUserEmail();
        verify(trackerEntityManager).getUniqueResult(eq(UserEntity.class), eq("user"), any(Criterion.class));
    }
}