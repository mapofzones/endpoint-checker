package com.mapofzones.endpointchecker.services.base;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class GenericService<T, ID, R extends GenericRepository<T, ID>> implements IGenericService<T, ID, R> {

    protected final R repository;

    public GenericService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void saveAll(List<T> list) {
        repository.saveAll(list);
    }

    @Override
    public void save(T object) {
        repository.save(object);
    }
}
