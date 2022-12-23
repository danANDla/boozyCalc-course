package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.RecipeEntity;
import com.danandla.boozyBack.entity.RecipeId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepo extends CrudRepository<RecipeEntity, RecipeId> {
    @Query(value = "SELECT * FROM RECIPES WHERE NAME = ?1", nativeQuery = true)
    RecipeEntity findByName(String name);
}
