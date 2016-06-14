package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.AccountEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface AccountDao extends CrudRepository<AccountEntity, Long> {
    @Query("SELECT initialAmount FROM account WHERE creator = :creator")
    BigDecimal getInitialAmountByCreator(final @Param("creator") UserEntity creator);
}
