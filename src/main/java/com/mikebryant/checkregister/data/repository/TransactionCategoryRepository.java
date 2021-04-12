package com.mikebryant.checkregister.data.repository;

import com.mikebryant.checkregister.data.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, String> {
    List<TransactionCategory> findAllByOrderBySortOrder();
    List<TransactionCategory> findAllByActiveIndIsTrueOrderBySortOrder();
}
