package com.mikebryant.checkregister.data.repository;

import com.mikebryant.checkregister.data.model.Transaction;
import com.mikebryant.checkregister.data.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, String> {
}
