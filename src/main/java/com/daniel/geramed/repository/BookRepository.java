package com.daniel.geramed.repository;

import com.daniel.geramed.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
    @Query(value = "SELECT * FROM m_book WHERE book_id = :id", nativeQuery = true)
    Optional<Book> findBookById(@Param("id") String id);

}
