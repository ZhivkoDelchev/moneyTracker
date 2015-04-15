package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

/**
 * Created by Jako on 13.4.2015 ã..
 */
@Dependent
public class UserDao {

    public UserEntity getUser(String email, EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        Criteria criteria = session.createCriteria(UserEntity.class, "user");
        criteria.add(Restrictions.eq("user.email", email));

        return (UserEntity) criteria.uniqueResult();
    }
}
