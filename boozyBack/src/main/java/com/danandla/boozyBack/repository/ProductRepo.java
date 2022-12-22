package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository <ProductEntity, Long> {
    @Query(value = "SELECT * FROM INGREDIENTS WHERE NAME = ?1", nativeQuery = true)
    ProductEntity findByName(String name);
}
