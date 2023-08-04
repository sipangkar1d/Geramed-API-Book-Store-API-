package com.daniel.geramed.controller;

import com.daniel.geramed.entity.Store;
import com.daniel.geramed.entity.UserCredential;
import com.daniel.geramed.model.request.StoreRequest;
import com.daniel.geramed.model.response.CommonResponse;
import com.daniel.geramed.model.response.StoreResponse;
import com.daniel.geramed.service.StoreService;
import com.daniel.geramed.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/stores")
public class StoreController {
    private final StoreService storeService;
    private final UserCredentialService userCredentialService;

    @PreAuthorize("hasAnyRole('STORE')")
    @PutMapping()
    public ResponseEntity<?> updateStore(@RequestBody StoreRequest request, Authentication authentication) {
        Store storeRequest = storeService.findById(request.getId());
        UserCredential userCredential = userCredentialService.getByEmail(authentication.getName());

        if (storeRequest.getEmail().equals(userCredential.getEmail())) {
            Store store = storeService.update(request);

            StoreResponse response = StoreResponse.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .phone(store.getPhone())
                    .email(store.getEmail())
                    .address(store.getAddress())
                    .isActive(store.isActive())
                    .build();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(CommonResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Success Update Store")
                            .data(response)
                            .build());
        }
        throw new AccessDeniedException("Access Denied, Can only update yours");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAllStore() {
        List<Store> stores = storeService.findAll();

        List<StoreResponse> responses = stores.stream().map(store ->
                StoreResponse.builder()
                        .id(store.getId())
                        .name(store.getName())
                        .phone(store.getPhone())
                        .email(store.getEmail())
                        .address(store.getAddress())
                        .isActive(store.isActive())
                        .build()
        ).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Get All Store")
                        .data(responses)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable(name = "id") String id) {
        storeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Delete Store")
                        .build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable String id) {
        Store store = storeService.findById(id);

        StoreResponse response = StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .phone(store.getPhone())
                .email(store.getEmail())
                .address(store.getAddress())
                .isActive(store.isActive())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Get Store")
                        .data(response)
                        .build());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(path = "/{id}")
    public ResponseEntity<?> activateStore(@PathVariable String id) {
        storeService.activate(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Activate Store")
                        .build());
    }
}
