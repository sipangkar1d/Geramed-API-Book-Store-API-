package com.daniel.geramed.service;

import com.daniel.geramed.model.request.OrderRequest;
import com.daniel.geramed.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    OrderResponse findById(String id);

    List<OrderResponse> findAll();
}
