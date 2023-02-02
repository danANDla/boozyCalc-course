package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.IngredientTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientTypeRepo extends CrudRepository<IngredientTypeEntity, Long> {
    @Query(value = "SELECT * FROM ingredient_types", nativeQuery = true)
    List<IngredientTypeEntity> findAll();
}
