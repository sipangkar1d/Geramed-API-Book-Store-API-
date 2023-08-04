package com.daniel.geramed.repository;

import com.daniel.geramed.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,String> {
    Optional<UserCredential> findByEmail(String email);
}
