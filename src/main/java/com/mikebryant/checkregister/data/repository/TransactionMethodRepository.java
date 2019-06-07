package com.mikebryant.checkregister.data.repository;

import com.mikebryant.checkregister.data.model.TransactionMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionMethodRepository extends JpaRepository<TransactionMethod, String> {
}
