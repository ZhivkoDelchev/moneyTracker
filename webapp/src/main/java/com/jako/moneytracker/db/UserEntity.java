package com.jako.moneytracker.db;

import com.jako.moneytracker.domain.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Jako on 17.1.2015 г..
 */
@Entity
@Table(name = "principles")
public class UserEntity extends User {

    private Long id;
    private Date createdDate;

    @Column(name = "principal_id")
    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Column(name = "password")
    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
