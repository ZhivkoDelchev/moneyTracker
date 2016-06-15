package com.jako.moneytracker.persistence.dao;

import com.jako.moneytracker.persistence.entity.PaymentCategoryEntity;
import com.jako.moneytracker.persistence.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CategoryDao extends CrudRepository<PaymentCategoryEntity, Long> {
    List<PaymentCategoryEntity> findByCreator(final UserEntity creator, final Sort sort);
    PaymentCategoryEntity findByIdAndCreator(final Long id, final UserEntity creator);
}
