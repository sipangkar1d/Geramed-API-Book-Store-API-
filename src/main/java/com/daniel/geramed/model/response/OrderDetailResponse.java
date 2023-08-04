package com.daniel.geramed.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class OrderDetailResponse {
    private String title;
    private String store;
    private Integer quantity;
    private Long price;
    private Long subTotal;
}

