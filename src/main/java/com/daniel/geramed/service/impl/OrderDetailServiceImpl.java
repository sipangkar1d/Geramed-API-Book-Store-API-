package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.OrderDetail;
import com.daniel.geramed.repository.OrderDetailRepository;
import com.daniel.geramed.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail create(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
