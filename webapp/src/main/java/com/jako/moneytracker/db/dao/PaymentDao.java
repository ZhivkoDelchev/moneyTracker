package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Jako on 16.2.2015 г..
 */
@Dependent
public class PaymentDao {

    public List<PaymentEntity> getUserPayments(String email, EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        Criteria paymentsCriteria = session.createCriteria(PaymentEntity.class, "payment");
        paymentsCriteria.createAlias("payment.creator", "creator");
        paymentsCriteria.add(Restrictions.eq("creator.email", email));

        return paymentsCriteria.list();
    }

    public void removePaymentsCategory(long categoryId, String email, EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        Criteria paymentsCriteria = session.createCriteria(PaymentEntity.class, "payment");
        paymentsCriteria.createAlias("payment.creator", "creator");
        paymentsCriteria.add(Restrictions.eq("creator.email", email));
        paymentsCriteria.createAlias("payment.category", "category");
        paymentsCriteria.add(Restrictions.eq("category.id", categoryId));

        List<PaymentEntity> payments = paymentsCriteria.list();
        for (PaymentEntity payment: payments) {
            payment.setCategory(null);
            session.update(payment);
        }
    }
}
