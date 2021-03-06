package com.jako.moneytracker.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "categories")
public class PaymentCategoryEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator")
    @JsonIgnore
    private UserEntity creator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
