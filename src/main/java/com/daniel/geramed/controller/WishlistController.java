package com.daniel.geramed.controller;

import com.daniel.geramed.model.request.WishlistRequest;
import com.daniel.geramed.model.response.CommonResponse;
import com.daniel.geramed.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/wishlists")
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody WishlistRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Add Wishlist")
                        .data(wishlistService.create(request))
                        .build());
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Add Wishlist")
                        .data(wishlistService.findAll())
                        .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        wishlistService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Delete Wishlist")
                        .build());
    }


}
