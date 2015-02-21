package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import com.jako.moneytracker.db.hibernate.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Jako on 16.2.2015 г..
 */
@Dependent
public class PaymentDao {

    @Inject
    private HibernateUtils hibernateUtils;

    public List<PaymentEntity> getUserPayments(UserEntity user) {
        Session session = hibernateUtils.getCurrentSession();
        Transaction transaction = hibernateUtils.beginTransaction(session);

        Criteria paymentsCriteria = session.createCriteria(PaymentEntity.class);
        paymentsCriteria.add(Restrictions.eq("creator", user.getId()));

        List<PaymentEntity> payments = paymentsCriteria.list();
        transaction.commit();

        return payments;
    }

    void setHibernateUtils(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }
}
