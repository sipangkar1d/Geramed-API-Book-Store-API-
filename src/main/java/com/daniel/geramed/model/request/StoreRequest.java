package com.daniel.geramed.model.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class StoreRequest {
    @NotBlank(message = "id is required")
    private String id;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "phone is required")
    private String phone;
    @NotBlank(message = "address is required")
    private String address;
}
