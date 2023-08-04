package com.daniel.geramed.controller;

import com.daniel.geramed.entity.Store;
import com.daniel.geramed.entity.UserCredential;
import com.daniel.geramed.model.request.BookRequest;
import com.daniel.geramed.model.response.CommonResponse;
import com.daniel.geramed.service.BookService;
import com.daniel.geramed.service.StoreService;
import com.daniel.geramed.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/books")
public class BookController {
    private final BookService bookService;
    private final UserCredentialService userCredentialService;
    private final StoreService storeService;


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Create Book")
                        .data(bookService.create(request))
                        .build());
    }

    @PostMapping(path = "/create-bulk")
    public ResponseEntity<?> createBulk(@RequestBody List<BookRequest> request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Success Create All Books")
                        .data(bookService.createBulk(request))
                        .build());
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody BookRequest request, Authentication authentication) {
        Store store = storeService.findById(request.getStoreId());
        UserCredential userCredential = userCredentialService.getByEmail(authentication.getName());
        if (store.getEmail().equals(userCredential.getEmail())){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(CommonResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Success Update Books")
                            .data(bookService.update(request))
                            .build());
        }
        throw new AccessDeniedException("Access Denied, Can only update yours");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findBookById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Find Book")
                        .data(bookService.findBookById(id))
                        .build());
    }
    @GetMapping()
    public ResponseEntity<?> findBookByTitleAuthorIsbn(
            @RequestParam(name = "title", required = false) String name,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "isbn", required = false) String isbn
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Find Books")
                        .data(bookService.findBookByTitleAuthorIsbn(name, author, isbn))
                        .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success Delete Books")
                        .build());
    }

}
