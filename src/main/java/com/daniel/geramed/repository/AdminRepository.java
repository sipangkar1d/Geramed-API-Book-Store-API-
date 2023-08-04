package com.daniel.geramed.repository;

import com.daniel.geramed.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    @Query(value = "select * from m_admin where email = :email", nativeQuery = true)
    Optional<Admin> findByEmail(@Param("email") String email);

}
