package com.mikebryant.checkregister.data.service;

import com.mikebryant.checkregister.data.model.TransactionMethod;
import com.mikebryant.checkregister.data.repository.TransactionMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionMethodService {
    @Autowired
    private TransactionMethodRepository repository;


    @Cacheable(value = "transactionMethod", key = "#uuid")
    public TransactionMethod get(String uuid) {
        Optional<TransactionMethod> entity = repository.findById(uuid);
        return entity.orElseThrow(() -> new EntityNotFoundException("No Transaction Method found with uuid " + uuid));
    }

    @CachePut(value = "transactionMethod", key = "#entity.uuid")
    public TransactionMethod save(TransactionMethod entity) {
        return repository.save(entity);
    }

//    @Cacheable(value = "transactionMethod")
    public List<TransactionMethod> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "description"));
    }

    @CacheEvict(value = "transactionMethod", key = "#uuid")
    public void delete(String uuid) {
        repository.deleteById(uuid);
    }
}
