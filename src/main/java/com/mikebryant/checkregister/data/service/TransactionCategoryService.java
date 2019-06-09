package com.mikebryant.checkregister.data.service;

import com.mikebryant.checkregister.data.model.TransactionCategory;
import com.mikebryant.checkregister.data.model.TransactionType;
import com.mikebryant.checkregister.data.repository.TransactionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionCategoryService {
    @Autowired
    private TransactionCategoryRepository repository;


    public TransactionCategory get(String uuid) {
        Optional<TransactionCategory> entity = repository.findById(uuid);
        return entity.orElseThrow(() -> new EntityNotFoundException("No Transaction Category found with uuid " + uuid));
    }

    public TransactionCategory save(TransactionCategory entity) {
        return repository.save(entity);
    }

    public List<TransactionCategory> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "transactionType.description"));
    }

    public List<TransactionCategory> getForTransactionType(TransactionType transactionType) {
        return repository.findByTransactionTypeUuid(transactionType.getUuid());
    }

    public void delete(String uuid) {
        repository.deleteById(uuid);
    }
}
