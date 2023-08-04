package com.daniel.geramed.repository;

import com.daniel.geramed.entity.BookPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookPriceRepository extends JpaRepository<BookPrice, String> {
}
