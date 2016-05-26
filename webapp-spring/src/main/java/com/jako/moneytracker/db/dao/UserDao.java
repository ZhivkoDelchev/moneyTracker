package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserDao extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(final String email);
}
