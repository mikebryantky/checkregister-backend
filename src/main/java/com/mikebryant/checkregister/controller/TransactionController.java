package com.mikebryant.checkregister.controller;

import com.mikebryant.checkregister.config.JsonMarshaller;
import com.mikebryant.checkregister.data.model.Transaction;
import com.mikebryant.checkregister.data.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService service;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @CrossOrigin
    @RequestMapping(
            value = "/transaction",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Transaction> add(@Valid @RequestBody Transaction transaction) {

        String receivedFromClientJson = jsonMarshaller.marshal(transaction);

        transaction = service.save(transaction);

        String returnedtoClientJson = jsonMarshaller.marshal(transaction);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transaction/{uuid}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<Transaction> get(@PathVariable String uuid) {
        Transaction transaction = service.get(uuid);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transaction",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Transaction>> getAll() {
        List<Transaction> transactions = service.getAll();

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
