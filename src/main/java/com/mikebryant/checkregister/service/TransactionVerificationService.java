package com.mikebryant.checkregister.service;

import com.mikebryant.checkregister.data.model.Transaction;
import com.mikebryant.checkregister.data.model.TransactionMethod;
import com.mikebryant.checkregister.data.model.TransactionType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TransactionVerificationService {

    public List<String> verify(@NonNull Transaction transaction) {
        List<String> errors = new ArrayList<>();

        if (TransactionType.WITHDRAWAL.equals(transaction.getTransactionType().getUuid())) {
            if (transaction.getDepositAmount() > 0) {
                errors.add("Deposit amount is not allowed if transaction is a withdrawal.");
            }

            if (TransactionMethod.CHECK.equals(transaction.getTransactionMethod().getUuid())
                    && StringUtils.isBlank(transaction.getCheckNumber())) {

                errors.add("Check number is required when paying by check.");
            }
        } else if (TransactionType.DEPOSIT.equals(transaction.getTransactionType().getUuid())) {
            if (transaction.getWithdrawalAmount() > 0) {
                errors.add("Withdrawal amount is not allowed if transaction is a deposit.");
            }
        }

        if (transaction.getTxDate() != null && transaction.getReconciledDate() != null) {
            if (transaction.getTxDate().isAfter(transaction.getReconciledDate())) {
                errors.add("Transaction date must be prior to reconciled date.");
            }
        }

        return errors;
    }

}
