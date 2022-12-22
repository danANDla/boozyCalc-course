package com.danandla.boozyBack.repository;

import com.danandla.boozyBack.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepo extends CrudRepository<PurchaseEntity, Long> {
    @Query(value = "SELECT * FROM PURCHASES WHERE NAME = ?1", nativeQuery = true)
    PurchaseEntity findByName(String name);
}
