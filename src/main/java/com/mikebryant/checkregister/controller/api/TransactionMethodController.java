package com.mikebryant.checkregister.controller.api;

import com.mikebryant.checkregister.data.model.TransactionMethod;
import com.mikebryant.checkregister.data.service.TransactionMethodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class TransactionMethodController {

    @Autowired
    private TransactionMethodService service;


    @CrossOrigin
    @RequestMapping(
            value = "/transactionMethod",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<TransactionMethod> add(@Valid @RequestBody TransactionMethod transactionMethod) {
        transactionMethod = service.save(transactionMethod);

        return new ResponseEntity<>(transactionMethod, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionMethod",
            method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity<TransactionMethod> update(@Valid @RequestBody TransactionMethod transactionMethod) {
        transactionMethod = service.save(transactionMethod);

        return new ResponseEntity<>(transactionMethod, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionMethod/{uuid}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<TransactionMethod> get(@PathVariable String uuid) {
        TransactionMethod transactionMethod = service.get(uuid);

        return new ResponseEntity<>(transactionMethod, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionMethod",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<TransactionMethod>> getAll() {
        List<TransactionMethod> transactionMethods = service.getAll();

        return new ResponseEntity<>(transactionMethods, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/transactionMethod/{uuid}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable String uuid) {
        log.debug("Delete transactionMethod: " + uuid);
        service.delete(uuid);

        return new ResponseEntity<>(" { \"status\": \"OK\" } ", HttpStatus.OK);
    }

}
