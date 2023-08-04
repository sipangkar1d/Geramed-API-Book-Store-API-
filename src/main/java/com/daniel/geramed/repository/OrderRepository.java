package com.daniel.geramed.repository;

import com.daniel.geramed.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
    @Query(value = "SELECT * FROM t_order WHERE order_id = :id", nativeQuery = true)
    Optional<Order> findOrderById(@Param("id") String id);
}
