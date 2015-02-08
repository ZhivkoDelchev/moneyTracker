package com.jako.moneytracker.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Jako on 8.2.2015 г..
 */
public class User {

    private String email;
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

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}
