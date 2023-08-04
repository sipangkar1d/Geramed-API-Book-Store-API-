package com.daniel.geramed.repository;

import com.daniel.geramed.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishList, String> {
}
