package com.jako.moneytracker.db.manager;

import com.jako.moneytracker.db.entity.BaseEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by Jako on 23.6.2015 г. ;)
 */
@RequestScoped
public class TrackerEntityManager {

    private final java.security.Principal userPrincipal;
    private EntityManager entityManager;

    @Inject
    public TrackerEntityManager(Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @Deprecated
    public TrackerEntityManager() {
        this(null);
    }

    @PersistenceContext(unitName = "tracker")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T extends BaseEntity> T getUniqueResult(Class<T> clazz, String alias, Criterion... restrictions) {
        Session session = getSession();

        Criteria criteria = session.createCriteria(clazz, alias);
        if (restrictions != null) {
            for (Criterion restriction : restrictions) {
                criteria.add(restriction);
            }
        }

        return clazz.cast(criteria.uniqueResult());
    }

    public <T extends BaseEntity> T getUniqueResultForCurrentUser(Class<T> clazz, String alias, Criterion... restrictions) {
        Session session = getSession();

        Criteria criteria = session.createCriteria(clazz, alias);
        criteria.createAlias(alias + ".creator", "creator");
        criteria.add(Restrictions.eq("creator.email", getUserEmail()));
        if (restrictions != null) {
            for (Criterion restriction : restrictions) {
                criteria.add(restriction);
            }
        }

        return clazz.cast(criteria.uniqueResult());
    }

    public <T extends BaseEntity> List<T> getResultsForCurrentUser(Class<T> clazz, String alias, Criterion... restrictions) {
        Session session = getSession();

        Criteria criteria = session.createCriteria(clazz, alias);
        criteria.createAlias(alias + ".creator", "creator");
        criteria.add(Restrictions.eq("creator.email", getUserEmail()));
        if (restrictions != null) {
            for (Criterion restriction : restrictions) {
                criteria.add(restriction);
            }
        }

        return criteria.list();
    }

    public UserEntity getCurrentUser() {
        String email = getUserEmail();
        return getUniqueResult(UserEntity.class, "user", Restrictions.eq("user.email", email));
    }

    public void delete(BaseEntity entity) {
        Session session = getSession();
        session.delete(entity);
    }

    public void persist(BaseEntity entity) {
        Date currentDate = new Date();
        entity.setCreatedDate(currentDate);
        entity.setLastEditDate(currentDate);

        Session session = getSession();
        session.persist(entity);
    }

    public void update(BaseEntity entity) {
        entity.setLastEditDate(new Date());

        Session session = getSession();
        session.update(entity);
    }

    private String getUserEmail() {
        return userPrincipal.getName();
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }
}
