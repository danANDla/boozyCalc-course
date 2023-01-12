package com.danandla.boozyBack.repository;

import com.danandla.boozyBack.entity.CocktailTypeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CocktailTypeRepo extends CrudRepository<CocktailTypeEntity, Long> {
    @Query(value = "SELECT * FROM cocktail_types", nativeQuery = true)
    List<CocktailTypeEntity> findAll();
}
