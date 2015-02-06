package com.jako.moneytracker.db;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Jako on 17.1.2015 г..
 */
@Entity
@Table(name = "principles")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "principal_id")
    private String email;
    @Column(name = "password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}
