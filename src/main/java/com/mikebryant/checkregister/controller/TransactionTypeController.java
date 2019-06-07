package com.mikebryant.checkregister.controller;

import com.mikebryant.checkregister.data.model.TransactionType;
import com.mikebryant.checkregister.data.service.TransactionTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class TransactionTypeController {

    @Autowired
    private TransactionTypeService service;


    @CrossOrigin
    @RequestMapping(
            value = "/transactionType",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<TransactionType> add(@Valid @RequestBody TransactionType transactionType) {
        transactionType = service.save(transactionType);

        return new ResponseEntity<>(transactionType, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionType",
            method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity<TransactionType> update(@Valid @RequestBody TransactionType transactionType) {
        transactionType = service.save(transactionType);

        return new ResponseEntity<>(transactionType, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionType/{uuid}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<TransactionType> get(@PathVariable String uuid) {
        TransactionType transactionType = service.get(uuid);

        return new ResponseEntity<>(transactionType, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionType",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<TransactionType>> getAll() {
        log.debug("Get all person");
        List<TransactionType> transactionTypes = service.getAll();

        return new ResponseEntity<>(transactionTypes, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionType/{uuid}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable String uuid) {
        log.debug("Delete transactionType: " + uuid);
        service.delete(uuid);

        return new ResponseEntity<>(" { \"status\": \"OK\" } ", HttpStatus.OK);
    }

}
