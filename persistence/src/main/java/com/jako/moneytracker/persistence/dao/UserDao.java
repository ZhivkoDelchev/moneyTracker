package com.jako.moneytracker.persistence.dao;

import com.jako.moneytracker.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserDao extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(final String email);
}
