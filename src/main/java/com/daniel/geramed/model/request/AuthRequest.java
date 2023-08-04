package com.daniel.geramed.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class AuthRequest {
    private String email;
    private String password;
}

