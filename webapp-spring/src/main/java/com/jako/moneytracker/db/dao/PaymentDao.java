package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.PaymentEntity;
import com.jako.moneytracker.db.entity.PaymentType;
import com.jako.moneytracker.db.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Transactional
public interface PaymentDao extends CrudRepository<PaymentEntity, Long> {
    @Modifying
    @Query("UPDATE payments SET category = NULL WHERE category = :category AND creator = :creator")
    void removeCategory(final @Param("category") PaymentCategoryEntity category, final @Param("creator") UserEntity creator);
    Page<PaymentEntity> findByCreator(final UserEntity creator, final Pageable pageable);
    @Query("SELECT SUM(amount) FROM payments WHERE creator = :creator AND paymentType = :type" )
    BigDecimal getSumAmountByCreatorAndType(final @Param("creator") UserEntity creator, final @Param("type") PaymentType type);
}
