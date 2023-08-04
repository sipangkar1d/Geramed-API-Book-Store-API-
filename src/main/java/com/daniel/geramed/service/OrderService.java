package com.daniel.geramed.service;

import com.daniel.geramed.model.request.OrderRequest;
import com.daniel.geramed.model.response.OrderResponse;

public interface OrderService {
    OrderResponse create(OrderRequest request);
}
