package com.mikebryant.checkregister.controller;

import com.mikebryant.checkregister.data.model.Transaction;
import com.mikebryant.checkregister.data.service.TransactionService;
import com.mikebryant.checkregister.service.TransactionVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService service;

    @Autowired
    private TransactionVerificationService transactionVerificationService;


    @CrossOrigin
    @RequestMapping(
            value = "/transaction",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Transaction> add(@Valid @RequestBody Transaction transaction) {
        return new ResponseEntity<>(service.save(transaction), HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transaction/{uuid}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<Transaction> get(@PathVariable String uuid) {
        return new ResponseEntity<>(service.get(uuid), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transaction",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Transaction>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/balance",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<Map<String, Double>> getBalance() {
        Map<String, Double> balanceData = new HashMap<>();
        balanceData.put("reconciled", service.getReconciledBalance());
        balanceData.put("unreconciled", service.getUnreconciledBalance());

        return new ResponseEntity<>(balanceData, HttpStatus.OK);
    }
}
