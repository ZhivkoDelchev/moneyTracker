package com.jako.moneytracker.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles",
        uniqueConstraints = @UniqueConstraint(
        columnNames = { "user_role", "user_id" }))
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @Column(name = "user_role", nullable = false, length = 45)
    private String role;

    private Long getId() {
        return id;
    }

    private void setId(final Long id) {
        this.id = id;
    }

    private UserEntity getUser() {
        return user;
    }

    private void setUser(final UserEntity user) {
        this.user = user;
    }

    private String getRole() {
        return role;
    }

    private void setRole(final String role) {
        this.role = role;
    }
}
