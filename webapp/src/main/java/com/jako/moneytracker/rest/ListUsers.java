package com.jako.moneytracker.rest;

import com.jako.moneytracker.db.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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

    private static final String PERSISTENCE_UNIT_NAME = "tracker";
    private static EntityManagerFactory factory;

    @GET
    public String get() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        // read the existing entries and write to console
        Query q = em.createQuery("select u from UserEntity u");
        List<UserEntity> todoList = q.getResultList();
        StringBuilder stringBuilder = new StringBuilder("Size: " + todoList.size());
        for (UserEntity userEntity : todoList) {
            stringBuilder.append("\r\n" + userEntity);
        }

        em.close();

        return stringBuilder.toString();
    }

    @POST
    public String post() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        // create new user
        em.getTransaction().begin();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("This is a mail");
        userEntity.setPassword("This is a password");
        em.persist(userEntity);
        em.getTransaction().commit();

        em.close();

        return userEntity.getId().toString();
    }
}
