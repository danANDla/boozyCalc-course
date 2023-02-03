package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.AvailableCocktailsEntity;
import com.danandla.boozyBack.entity.PartyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvailableCocktailRepo extends CrudRepository<AvailableCocktailsEntity, Long> {
    @Query(value = "SELECT * FROM get_available_cocktails(?1)", nativeQuery = true)
    List<AvailableCocktailsEntity> getByParty(Long id);
}
