package com.daniel.geramed.controller;

import com.daniel.geramed.model.request.OrderRequest;
import com.daniel.geramed.model.response.CommonResponse;
import com.daniel.geramed.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody OrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Create Order")
                        .data(orderService.create(request))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Find by Id Order")
                        .data(orderService.findById(id))
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Find All Order")
                        .data(orderService.findAll())
                        .build());
    }
}
