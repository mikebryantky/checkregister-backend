package com.mikebryant.checkregister.data.repository;

import com.mikebryant.checkregister.data.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query(value = "select sum(t.amount) from Transaction t where t.reconciledDate is null")
    Double getUnreconciledBalance();

    @Query(value = "select sum(t.amount) from Transaction t where t.reconciledDate is not null")
    Double getReconciledBalance();

}
