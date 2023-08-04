package com.daniel.geramed.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class CustomerResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
