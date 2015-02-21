package com.jako.moneytracker.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by Jako on 15.2.2015 г..
 */
@Entity(name = "categories")
public class PaymentCategoryEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator")
    private UserEntity creator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "PaymentCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}
