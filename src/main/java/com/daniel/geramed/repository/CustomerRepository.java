package com.daniel.geramed.repository;

import com.daniel.geramed.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query(value = "SELECT * FROM m_custommer WHERE customer_id = :id", nativeQuery = true)
    Customer findCustomerById(@Param("id") String id);
}
