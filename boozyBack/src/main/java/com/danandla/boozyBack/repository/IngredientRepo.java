package com.danandla.boozyBack.repository;

import com.danandla.boozyBack.entity.IngredientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepo extends CrudRepository <IngredientEntity, Long> {
    @Query(value = "SELECT * FROM INGREDIENTS WHERE NAME = ?1", nativeQuery = true)
    IngredientEntity findByName(String name);

    @Query(value = "SELECT DISTINCT type FROM ingredients order by type", nativeQuery = true)
    List<Long> findAllTypes();
}
