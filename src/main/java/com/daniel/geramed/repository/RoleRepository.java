package com.daniel.geramed.repository;

import com.daniel.geramed.entity.Role;
import com.daniel.geramed.entity.constant.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
