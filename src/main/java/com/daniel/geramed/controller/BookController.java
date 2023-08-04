package com.daniel.geramed.controller;

import com.daniel.geramed.entity.Book;
import com.daniel.geramed.model.request.BookRequest;
import com.daniel.geramed.model.response.CommonResponse;
import com.daniel.geramed.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/books")
public class BookController {
    private final BookService bookService;
    public ResponseEntity<?> create(@RequestBody BookRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Update Store")
                        .data(bookService.create(request))
                        .build());

    }
}
