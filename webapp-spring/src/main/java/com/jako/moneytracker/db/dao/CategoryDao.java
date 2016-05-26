package com.jako.moneytracker.db.dao;

import com.jako.moneytracker.db.entity.PaymentCategoryEntity;
import com.jako.moneytracker.db.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CategoryDao extends CrudRepository<PaymentCategoryEntity, Long> {
    List<PaymentCategoryEntity> findByCreator(final UserEntity creator);
    PaymentCategoryEntity findByCreatorAndName(final UserEntity user, final String name);
    PaymentCategoryEntity findByIdAndCreator(final Long id, final UserEntity creator);
}
