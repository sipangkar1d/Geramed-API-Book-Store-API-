package com.daniel.geramed.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private String bookPriceId;
    private Integer quantity;
}

