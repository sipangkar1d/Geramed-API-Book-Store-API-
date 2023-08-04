package com.daniel.geramed.repository;

import com.daniel.geramed.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishList, String> {
    @Query(value = "SELECT * FROM m_wishlist", nativeQuery = true)
    List<WishList> findAllWishlist();

    @Query(value = "SELECT * FROM m_wishlist WHERE wishlist_id = :id", nativeQuery = true)
    Optional<WishList> findWishListById(@Param("id") String id);
}
