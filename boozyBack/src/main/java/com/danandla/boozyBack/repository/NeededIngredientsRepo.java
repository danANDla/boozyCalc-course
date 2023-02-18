package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.NeededIngredientsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NeededIngredientsRepo extends CrudRepository<NeededIngredientsEntity, Long> {
    @Query(value = "SELECT * FROM get_needed_ingredients(?1, ?2)", nativeQuery = true)
    List<NeededIngredientsEntity> getNeeded(Long party_id, Long ingredient_id);
}
