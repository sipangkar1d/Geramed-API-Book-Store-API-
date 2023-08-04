package com.daniel.geramed.service;

import com.daniel.geramed.entity.Book;
import com.daniel.geramed.model.request.BookRequest;
import com.daniel.geramed.model.response.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse create(BookRequest request);
    List<BookResponse> createBulk(List<BookRequest> requests);
    BookResponse update(BookRequest request);
    Book findById(String id);
    BookResponse findBookById(String id);
    List<BookResponse> findBookByTitleAuthorIsbn(String name, String author, String isbn);
    void delete(String id);
}
