package com.daniel.geramed.service;

import com.daniel.geramed.entity.Store;
import com.daniel.geramed.model.request.StoreRequest;

import java.util.List;

public interface StoreService {
    Store create(Store request);
    Store findById(String id);
    Store update(StoreRequest store);
    void delete(String id);
    List<Store> findAll();
    void activate(String id);
}
