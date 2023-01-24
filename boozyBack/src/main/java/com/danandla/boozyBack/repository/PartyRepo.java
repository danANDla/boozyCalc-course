package com.danandla.boozyBack.repository;

import com.danandla.boozyBack.entity.PartyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PartyRepo extends CrudRepository<PartyEntity, Long> {
    @Query(value = "SELECT * FROM PARTIES WHERE NAME = ?1", nativeQuery = true)
    PartyEntity findByName(String name);
}
