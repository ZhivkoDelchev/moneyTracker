package com.jako.moneytracker.db.manager;

import com.jako.moneytracker.db.entity.BaseEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;

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

    @PersistenceContext(unitName = "tracker")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T extends BaseEntity> T getUniqueResult(Class<T> clazz, String alias, Criterion... restrictions) {
        Session session = entityManager.unwrap(Session.class);

        Criteria criteria = session.createCriteria(clazz, alias);
        if (restrictions != null) {
            for (Criterion restriction : restrictions) {
                criteria.add(restriction);
            }
        }

        return clazz.cast(criteria.uniqueResult());
    }

    public String getUserEmail() {
        return userPrincipal.getName();
    }
}
