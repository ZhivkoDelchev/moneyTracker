package com.jako.moneytracker.persistence.dao;

import com.jako.moneytracker.persistence.entity.AccountEntity;
import com.jako.moneytracker.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface AccountDao extends CrudRepository<AccountEntity, Long> {
    @Query("SELECT initialAmount FROM account WHERE creator = :creator")
    BigDecimal getInitialAmountByCreator(final @Param("creator") UserEntity creator);
}
