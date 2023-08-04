package com.daniel.geramed.controller;

import com.daniel.geramed.entity.Customer;
import com.daniel.geramed.entity.UserCredential;
import com.daniel.geramed.model.request.CustomerRequest;
import com.daniel.geramed.model.response.CommonResponse;
import com.daniel.geramed.model.response.CustomerResponse;
import com.daniel.geramed.service.CustomerService;
import com.daniel.geramed.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final UserCredentialService userCredentialService;

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody CustomerRequest request, Authentication authentication) {
        Customer customer = customerService.findById(request.getId());
        UserCredential userCredential = userCredentialService.getByEmail(authentication.getName());

        if (customer.getEmail().equals(userCredential.getEmail())) {
            Customer customerUpdated = customerService.update(request);
            CustomerResponse response = CustomerResponse.builder()
                    .id(customerUpdated.getId())
                    .name(customerUpdated.getName())
                    .email(customerUpdated.getEmail())
                    .phone(customerUpdated.getPhone())
                    .address(customerUpdated.getAddress())
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(CommonResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Success Update Customer")
                            .data(response)
                            .build());
        }
        throw new AccessDeniedException("Access Denied, Can only update yours");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Customer customer = customerService.findById(id);
        CustomerResponse response = CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Get Customer")
                        .data(response)
                        .build());
    }
}
