package com.daniel.geramed.service;

import com.daniel.geramed.entity.Store;

import java.util.List;

public interface StoreService {
    Store create(Store request);
    Store findById(String id);
    Store update(Store store);
    void delete(Store store);
    List<Store> findAll();
}
