package com.jako.moneytracker.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "payments")
@JsonIgnoreProperties({ "internal" })
public class PaymentEntity extends BaseEntity {

    @Column( scale = 2, precision = 12)
    private BigDecimal amount;
    @OneToOne
    @JoinColumn(name = "category_id")
    private PaymentCategoryEntity category;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name = "creator")
    private UserEntity creator;
    @Column(name = "note")
    private String note;
    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(PaymentCategoryEntity category) {
        this.category = category;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
