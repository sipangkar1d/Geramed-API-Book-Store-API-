package com.daniel.geramed.model.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
@Getter
public class OrderResponse {
    private String invoice;
    private String orderDate;
    private List<OrderDetailResponse> orderDetails;
    private Long grandTotal;

}
