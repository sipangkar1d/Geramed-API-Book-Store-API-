package com.daniel.geramed.repository;

import com.daniel.geramed.entity.Role;
import com.daniel.geramed.entity.constant.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value = "SELECT * FROM m_role WHERE role = :role", nativeQuery = true)
    Optional<Role> findByRole(ERole role);
}
