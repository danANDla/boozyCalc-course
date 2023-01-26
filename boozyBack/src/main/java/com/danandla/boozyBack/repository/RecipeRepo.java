package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.RecipeEntity;
import com.danandla.boozyBack.entity.RecipeId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeRepo extends CrudRepository<RecipeEntity, RecipeId> {
    @Query(value = "SELECT * FROM RECIPES WHERE NAME = ?1", nativeQuery = true)
    RecipeEntity findByName(String name);

    @Query(value = "SELECT * FROM RECIPES WHERE COCKTAIL_ID = ?1", nativeQuery = true)
    List<RecipeEntity> findByCocktailId(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM RECIPES WHERE COCKTAIL_ID = ?1", nativeQuery = true)
    void deleteByCocktailId(Long id);

}
