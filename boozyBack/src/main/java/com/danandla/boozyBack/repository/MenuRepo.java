package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.MenuEntity;
import com.danandla.boozyBack.entity.MenuId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MenuRepo extends CrudRepository<MenuEntity, MenuId> {
    @Query(value = "SELECT * FROM MENUS WHERE party_id = ?1", nativeQuery = true)
    ArrayList<MenuEntity> findByPartId(Long partyId);
}
