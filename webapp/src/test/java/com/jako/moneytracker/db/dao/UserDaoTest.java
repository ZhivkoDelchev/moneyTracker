package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jako on 13.4.2015 ã..
 */
public class UserDaoTest {

    private UserDao sut;

    @Before
    public void setUp() throws Exception {
        sut = new UserDao();
    }

    @Test
    public void testQueryUserByEmailAndReturnSingleUniqueResult() throws Exception {
        Criteria criteria = mock(Criteria.class);

        Session session = mock(Session.class);
        when(session.createCriteria(UserEntity.class, "user")).thenReturn(criteria);

        EntityManager entityManager = mock(EntityManager.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        sut.getUser("mail", entityManager);

        verify(criteria).uniqueResult();
    }
}