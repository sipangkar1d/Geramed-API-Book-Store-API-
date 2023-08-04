package com.daniel.geramed.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(toBuilder = true)
public class StoreResponse {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private boolean isActive;
}
