package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.Store;
import com.daniel.geramed.model.request.StoreRequest;
import com.daniel.geramed.repository.StoreRepository;
import com.daniel.geramed.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    @Override
    public Store create(Store request) {
        return storeRepository.saveAndFlush(request);
    }

    @Override
    public Store findById(String id) {
        return storeRepository.findStoreById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not Found"));
    }

    @Override
    public Store update(StoreRequest request) {
        Store store = findById(request.getId());
        store.setName(request.getName());
        store.setPhone(request.getPhone());
        store.setAddress(request.getAddress());
        return storeRepository.save(store);
    }

    @Override
    public void delete(String id) {
        Store store = findById(id);
        storeRepository.deactivateStore(id);
    }

    @Override
    public void activate(String id) {
        Store store = findById(id);
        storeRepository.activateStore(id);
    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAllStores();
    }
}
