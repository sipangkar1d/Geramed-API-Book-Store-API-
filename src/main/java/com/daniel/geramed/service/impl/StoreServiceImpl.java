package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.Store;
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
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not Found"));
    }

    @Override
    public Store update(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public void delete(Store request) {
        Store store = findById(request.getId());
        storeRepository.delete(store);
    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }
}
