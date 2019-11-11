package com.mikebryant.checkregister.data.service;

import com.mikebryant.checkregister.data.model.TransactionCategory;
import com.mikebryant.checkregister.data.repository.TransactionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionCategoryService {
    @Autowired
    private TransactionCategoryRepository repository;


    @Cacheable(value = "transactionCategory", key = "#uuid")
    public TransactionCategory get(String uuid) {
        Optional<TransactionCategory> entity = repository.findById(uuid);
        return entity.orElseThrow(() -> new EntityNotFoundException("No Transaction Category found with uuid " + uuid));
    }

    @Cacheable(value = "transactionCategoryAll")
    public List<TransactionCategory> getAll() {
        return repository.findAll();
    }

    @Caching(evict = {
            @CacheEvict(value = "transactionCategory", key = "#entity.uuid"),
            @CacheEvict(value = "transactionCategoryAll", allEntries = true)
    })
    public TransactionCategory save(TransactionCategory entity) {
        return repository.save(entity);
    }

    @Caching(evict = {
            @CacheEvict(value = "transactionCategory", key = "#uuid"),
            @CacheEvict(value = "transactionCategoryAll", allEntries = true)
    })
    public void delete(String uuid) {
        repository.deleteById(uuid);
    }
}
