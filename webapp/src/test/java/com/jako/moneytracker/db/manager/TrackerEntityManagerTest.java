package com.jako.moneytracker.db.manager;

import com.jako.moneytracker.db.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jako on 23.6.2015 г. ;)
 */
public class TrackerEntityManagerTest {

    private TrackerEntityManager sut;
    @Mock private Principal principal;
    @Mock private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new TrackerEntityManager(principal);
        sut.setEntityManager(entityManager);
    }

    @Test
    public void userEmailShouldBePrincipalName() throws Exception {
        String name = "name";
        when(principal.getName()).thenReturn(name);

        assertEquals(name, sut.getUserEmail());
        verify(principal).getName();
    }

    @Test
    public void shouldCreateCriteriaForRequestedClassAndGetUniqueResult() throws Exception {
        String alias = "alias";
        Class<UserEntity> clazz = UserEntity.class;

        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        Criteria criteria = mock(Criteria.class);
        when(session.createCriteria(clazz, alias)).thenReturn(criteria);

        sut.getUniqueResult(clazz, alias);

        verify(entityManager).unwrap(Session.class);
        verify(session).createCriteria(clazz, alias);
    }

    @Test
    public void shouldCreateCriteriaForRequestedClassAndAddRestrictions() throws Exception {
        String alias = "alias";
        Class<UserEntity> clazz = UserEntity.class;

        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        Criteria criteria = mock(Criteria.class);
        when(session.createCriteria(clazz, alias)).thenReturn(criteria);

        sut.getUniqueResult(clazz, alias);

        verify(entityManager).unwrap(Session.class);
        verify(session).createCriteria(clazz, alias);
    }

    @Test
    public void shouldCreateCriteriaForRequestedClassAndListAllRecordsForCurrentUser() throws Exception {
        String alias = "alias";
        Class<UserEntity> clazz = UserEntity.class;

        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        Criteria criteria = mock(Criteria.class);
        when(session.createCriteria(clazz, alias)).thenReturn(criteria);

        sut.getResultsForCurrentUser(clazz, alias);

        verify(entityManager).unwrap(Session.class);
        verify(session).createCriteria(clazz, alias);
        verify(criteria).createAlias("alias.creator", "creator");
        // TODO : verify if restriction for users was added.
    }
}