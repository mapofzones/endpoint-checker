package com.mapofzones.endpointchecker.services.base;

import java.util.List;

public interface IGenericService<T, ID, R extends GenericRepository<T, ID>> {

    List<T> findAll();
    void saveAll(List<T> list);

}
