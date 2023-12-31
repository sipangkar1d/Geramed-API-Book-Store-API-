package com.daniel.geramed.repository;

import com.daniel.geramed.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,String> {
    @Query(value = "SELECT * FROM m_user_credential WHERE email = :email",nativeQuery = true)
    Optional<UserCredential> findByEmail(@Param("email")String email);
}
