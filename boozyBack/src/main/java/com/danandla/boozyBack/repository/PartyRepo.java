package com.danandla.boozyBack.repository;

import com.danandla.boozyBack.entity.CocktailEntity;
import com.danandla.boozyBack.entity.PartyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PartyRepo extends CrudRepository<PartyEntity, Long> {
    @Query(value = "SELECT * FROM PARTIES WHERE NAME = ?1", nativeQuery = true)
    PartyEntity findByName(String name);

    @Query(value = "SELECT * FROM PARTIES ORDER BY parties.event_date DESC", nativeQuery = true)
    List<PartyEntity> findAllDateSorted();
}
