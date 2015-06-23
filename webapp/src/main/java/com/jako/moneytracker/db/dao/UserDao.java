package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Created by Jako on 13.4.2015 �..
 */
@Dependent
public class UserDao {

    private final TrackerEntityManager trackerEntityManager;

    @Inject
    public UserDao(TrackerEntityManager trackerEntityManager) {
        this.trackerEntityManager = trackerEntityManager;
    }

    public UserEntity getUser() {
        String email = trackerEntityManager.getUserEmail();
        return trackerEntityManager.getUniqueResult(UserEntity.class, "user", Restrictions.eq("user.email", email));
    }
}
