package com.daniel.geramed.repository;

import com.daniel.geramed.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String> {
}
