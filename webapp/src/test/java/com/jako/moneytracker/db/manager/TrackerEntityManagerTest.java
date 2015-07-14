package com.jako.moneytracker.db.manager;

import com.jako.moneytracker.db.entity.BaseEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.security.Principal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Jako on 23.6.2015 г. ;)
 */
public class TrackerEntityManagerTest {

    private TrackerEntityManager sut;
    @Mock
    private Principal principal;
    @Mock
    private EntityManager entityManager;

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
    public void shouldCreateCriteriaForRequestedClassAndGetUniqueResultForCurrentUser() throws Exception {
        String alias = "alias";
        Class<UserEntity> clazz = UserEntity.class;

        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        Criteria criteria = mock(Criteria.class);
        when(session.createCriteria(clazz, alias)).thenReturn(criteria);

        sut.getUniqueResultForCurrentUser(clazz, alias);

        verify(entityManager).unwrap(Session.class);
        verify(session).createCriteria(clazz, alias);
        verify(criteria).createAlias("alias.creator", "creator");
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

    @Test
    public void shouldDeleteEntity() throws Exception {
        Session session = mock(Session.class);

        when(entityManager.unwrap(Session.class)).thenReturn(session);
        BaseEntity entity = mock(BaseEntity.class);

        sut.delete(entity);

        verify(session).delete(entity);
    }

    @Test
    public void shouldSetCurrentDateForCreatedAndLastChangedAndPersistTheEntity() throws Exception {
        BaseEntity entity = mock(BaseEntity.class);

        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        sut.persist(entity);
//        Possible random failing test.
//        Date currentDate = new Date();
//        verify(entity).setCreatedDate(currentDate);
//        verify(entity).setLastEditDate(currentDate);
        verify(entity).setCreatedDate(any(Date.class));
        verify(entity).setLastEditDate(any(Date.class));
        verify(session).persist(entity);
    }

    @Test
    public void shouldSetLastEditDateAndUpdateEntity() throws Exception {
        BaseEntity entity = mock(BaseEntity.class);

        Session session = mock(Session.class);
        when(entityManager.unwrap(Session.class)).thenReturn(session);

        sut.update(entity);

//        Possible random failing test.
//        verify(entity).setCreatedDate(new Date());
        verify(entity).setLastEditDate(any(Date.class));
        verify(session).update(entity);
    }
}