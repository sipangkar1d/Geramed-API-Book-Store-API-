package com.daniel.geramed.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private String customerId;
    private List<OrderDetailRequest> orderDetails;
}

