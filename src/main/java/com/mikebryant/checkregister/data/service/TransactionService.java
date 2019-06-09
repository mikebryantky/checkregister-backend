package com.mikebryant.checkregister.data.service;

import com.mikebryant.checkregister.data.model.Transaction;
import com.mikebryant.checkregister.data.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository repository;


    public Transaction get(String uuid) {
        Optional<Transaction> optionalDepartment = repository.findById(uuid);
        return optionalDepartment.orElseThrow(() -> new EntityNotFoundException("No Transaction found with uuid " + uuid));
    }

    public Transaction save(Transaction entity) {
        return repository.save(entity);
    }

    public List<Transaction> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "txDate"));
    }

    public void delete (String uuid) {
        repository.deleteById(uuid);
    }

}
