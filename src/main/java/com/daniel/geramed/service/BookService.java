package com.daniel.geramed.service;

import com.daniel.geramed.entity.Book;
import com.daniel.geramed.model.request.BookRequest;

import java.util.List;

public interface BookService {
    Book create(BookRequest request);
    List<Book> createBulk(List<BookRequest> requests);
    Book update(BookRequest request);
    Book findById(String id);
    List<Book> findBookByTitleAuthorIsbn(String name, String author, String isbn);
    void delete(String id);



}
