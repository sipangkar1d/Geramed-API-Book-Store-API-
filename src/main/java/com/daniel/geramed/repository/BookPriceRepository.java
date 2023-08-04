package com.daniel.geramed.repository;

import com.daniel.geramed.entity.BookPrice;
import com.daniel.geramed.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookPriceRepository extends JpaRepository<BookPrice, String> {
    @Query(value = "SELECT * FROM m_book_price WHERE book_price_id = :id", nativeQuery = true)
    Optional<BookPrice> findBookPriceById(@Param("id") String id);

}
