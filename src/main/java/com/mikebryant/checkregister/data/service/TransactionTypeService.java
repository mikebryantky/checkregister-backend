package com.mikebryant.checkregister.data.service;

import com.mikebryant.checkregister.data.model.TransactionType;
import com.mikebryant.checkregister.data.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionTypeService {
    @Autowired
    private TransactionTypeRepository repository;


    public TransactionType get(String uuid) {
        Optional<TransactionType> entity = repository.findById(uuid);
        return entity.orElseThrow(() -> new EntityNotFoundException("No Transaction Type found with uuid " + uuid));
    }

    public TransactionType save(TransactionType entity) {
        return repository.save(entity);
    }

    public List<TransactionType> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "description"));
    }

    public void delete(String uuid) {
        repository.deleteById(uuid);
    }
}
