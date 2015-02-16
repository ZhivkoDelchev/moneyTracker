package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.hibernate.HibernateUtils;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by Jako on 17.1.2015 г..
 */
@Stateless
@Path("/users")
public class ListUsers {

    @Inject
    private HibernateUtils hibernateUtils;

    @GET
    public String get() {
        Session session = hibernateUtils.getSessionFactory().getCurrentSession();

        session.beginTransaction();

        List<UserEntity> result = session.createCriteria(UserEntity.class).list();
        session.getTransaction().commit();
        StringBuilder stringBuilder = new StringBuilder("Size: " + result.size());
        for (UserEntity userEntity : result) {
            stringBuilder.append("\r\n").append(userEntity);
        }

        return stringBuilder.toString();
    }

    @POST
    public String post() {
        Session session = hibernateUtils.getSessionFactory().getCurrentSession();

        session.beginTransaction();

        // create new user
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("This is a mail");
        userEntity.setPassword("This is a password");
        session.persist(userEntity);
        session.getTransaction().commit();

        return userEntity.getId().toString();
    }
}
