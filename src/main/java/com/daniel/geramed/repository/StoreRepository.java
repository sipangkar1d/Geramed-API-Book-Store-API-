package com.daniel.geramed.repository;

import com.daniel.geramed.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, String> {
    @Query(value = "SELECT * FROM m_store WHERE store_id = :id", nativeQuery = true)
    Optional<Store> findStoreById(@Param("id") String id);

    @Query(value = "SELECT * FROM m_store", nativeQuery = true)
    List<Store> findAllStores();

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_store SET is_active = true WHERE store_id = :id", nativeQuery = true)
    void activateStore(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE m_store SET is_active = false WHERE store_id = :id", nativeQuery = true)
    void deactivateStore(@Param("id") String id);
}
