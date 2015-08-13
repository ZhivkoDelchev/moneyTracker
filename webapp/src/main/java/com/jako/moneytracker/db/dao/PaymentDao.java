package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Jako on 16.2.2015 г..
 */
@Dependent
public class PaymentDao {

    private final TrackerEntityManager trackerEntityManager;

    @Inject
    public PaymentDao(TrackerEntityManager trackerEntityManager) {
        this.trackerEntityManager = trackerEntityManager;
    }

    public List<PaymentEntity> getUserPayments() {
        return trackerEntityManager.getResultsForCurrentUser(PaymentEntity.class, "payment");
    }

    public void removePaymentsCategory(long categoryId) {
        SimpleExpression categoryIs = Restrictions.eq("payment.category.id", categoryId);
        List<PaymentEntity> payments = trackerEntityManager.getResultsForCurrentUser(PaymentEntity.class, "payment", categoryIs);
        payments.stream().forEach(payment -> removeCategoryFromPayment(payment));
    }

    private void removeCategoryFromPayment(PaymentEntity payment) {
        payment.setCategory(null);
        trackerEntityManager.update(payment);
    }
}
