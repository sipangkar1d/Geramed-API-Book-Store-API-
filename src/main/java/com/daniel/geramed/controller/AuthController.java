package com.daniel.geramed.controller;

import com.daniel.geramed.model.request.AuthRequest;
import com.daniel.geramed.model.response.CommonResponse;
import com.daniel.geramed.model.response.LoginResponse;
import com.daniel.geramed.model.response.RegisterResponse;
import com.daniel.geramed.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        LoginResponse response = authService.login(request);

        CommonResponse<?> commonResponse = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Login")
                .data(response)
                .build();

        return ResponseEntity.status(commonResponse.getStatusCode())
                .body(commonResponse);
    }

    @PostMapping(path = "/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        RegisterResponse response = authService.registerAdmin(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Register Admin")
                        .data(response)
                        .build());
    }

    @PostMapping(path = "/register-store")
    public ResponseEntity<?> registerStore(@RequestBody AuthRequest request) {
        RegisterResponse response = authService.registerStore(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Register Store")
                        .data(response)
                        .build());
    }

    @PostMapping(path = "/register-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody AuthRequest request) {
        RegisterResponse response = authService.registerCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Register Customer")
                        .data(response)
                        .build());
    }
}
