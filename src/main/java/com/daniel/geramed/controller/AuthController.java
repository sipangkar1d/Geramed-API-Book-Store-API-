package com.daniel.geramed.controller;

import com.daniel.geramed.model.request.AuthRequest;
import com.daniel.geramed.model.response.CommonResponse;
import com.daniel.geramed.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Login")
                        .data(authService.login(request))
                        .build());
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(path = "/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Register Admin")
                        .data(authService.registerAdmin(request))
                        .build());
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(path = "/register-store")
    public ResponseEntity<?> registerStore(@RequestBody AuthRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Register Store")
                        .data(authService.registerStore(request))
                        .build());
    }

    @PostMapping(path = "/register-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody AuthRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Register Customer")
                        .data(authService.registerCustomer(request))
                        .build());
    }
}
