package com.jako.moneytracker.persistence.dao;

import com.jako.moneytracker.persistence.entity.UserEntity;
import com.jako.moneytracker.persistence.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRoleDao extends CrudRepository<UserRole, Long> {
    @Query("SELECT role FROM UserRole where user = :user")
    List<String> findRoleByUser(final @Param("user") UserEntity user);
}
