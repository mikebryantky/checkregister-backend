package com.mikebryant.checkregister.controller.api;

import com.mikebryant.checkregister.data.model.TransactionCategory;
import com.mikebryant.checkregister.data.service.TransactionCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class TransactionCategoryController {

    @Autowired
    private TransactionCategoryService service;


    @CrossOrigin
    @RequestMapping(
            value = "/transactionCategory",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<TransactionCategory> add(@Valid @RequestBody TransactionCategory transactionType) {
        transactionType = service.save(transactionType);

        return new ResponseEntity<>(transactionType, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionCategory",
            method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity<TransactionCategory> update(@Valid @RequestBody TransactionCategory transactionType) {
        transactionType = service.save(transactionType);

        return new ResponseEntity<>(transactionType, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionCategory/{uuid}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<TransactionCategory> get(@PathVariable String uuid) {
        TransactionCategory transactionType = service.get(uuid);

        return new ResponseEntity<>(transactionType, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionCategory",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<TransactionCategory>> getAll(
            @RequestParam(required = false, value = "all") boolean all) {

        List<TransactionCategory> transactionTypes = all ? service.getAll() : service.getAllActive();

        return new ResponseEntity<>(transactionTypes, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionCategory/{uuid}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable String uuid) {
        service.delete(uuid);

        return new ResponseEntity<>(" { \"status\": \"OK\" } ", HttpStatus.OK);
    }

}
