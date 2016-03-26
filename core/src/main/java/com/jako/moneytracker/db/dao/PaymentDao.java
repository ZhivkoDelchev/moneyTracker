package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.manager.TrackerEntityManager;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Dependent
public class PaymentDao {

    private final TrackerEntityManager trackerEntityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Inject
    public PaymentDao(TrackerEntityManager trackerEntityManager, CriteriaBuilder criteriaBuilder) {
        this.trackerEntityManager = trackerEntityManager;
        this.criteriaBuilder = criteriaBuilder;
    }

    public List<PaymentEntity> getUserPayments() {
        Order[] orders = {criteriaBuilder.descending("date"), criteriaBuilder.descending("createdDate")};
        return trackerEntityManager.getResultsForCurrentUser(PaymentEntity.class, "payment", 20, orders, null);
    }

    public void removePaymentsCategory(long categoryId) {
        SimpleExpression equalsCriteria = criteriaBuilder.buildEqualsCriteria("payment.category.id", categoryId);
        List<PaymentEntity> payments = trackerEntityManager.getResultsForCurrentUser(PaymentEntity.class, "payment", equalsCriteria);
        payments.stream().forEach(this::removeCategoryFromPayment);
    }

    public void createPayment(BigDecimal amount, PaymentType type, PaymentCategoryEntity category, String note, Date date) {
        PaymentEntity payment = new PaymentEntity();

        payment.setCategory(category);
        payment.setAmount(amount);
        payment.setPaymentType(type);
        payment.setNote(note);
        payment.setDate(date);
        payment.setCreator(trackerEntityManager.getCurrentUser());

        trackerEntityManager.persist(payment);
    }

    private void removeCategoryFromPayment(PaymentEntity payment) {
        payment.setCategory(null);
        trackerEntityManager.update(payment);
    }
}
