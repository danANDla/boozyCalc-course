package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.InviteEntity;
import com.danandla.boozyBack.entity.InviteId;
import com.danandla.boozyBack.entity.MenuEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface InviteRepo extends CrudRepository<InviteEntity, InviteId> {
    @Query(value = "SELECT * FROM INVITES WHERE party_id = ?1", nativeQuery = true)
    ArrayList<InviteEntity> findByPartId(Long partyId);
}
