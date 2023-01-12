package com.danandla.boozyBack.repository;

import com.danandla.boozyBack.entity.CocktailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CocktailRepo extends CrudRepository<CocktailEntity, Long> {
    @Query(value = "SELECT * FROM cocktails WHERE NAME = ?1", nativeQuery = true)
    CocktailEntity findByName(String name);

    @Query(value = "SELECT * FROM cocktails", nativeQuery = true)
    List<CocktailEntity> findAll();

    @Query(value = "SELECT DISTINCT type_id FROM cocktails", nativeQuery = true)
    List<Long> findAllTypes();
}
