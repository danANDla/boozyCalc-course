package com.danandla.boozyBack.repository;


import com.danandla.boozyBack.entity.GroupedOrderItemEntity;
import com.danandla.boozyBack.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<OrderEntity, Long> {
    @Query(value = "SELECT * FROM ORDERS WHERE party_id = ?1", nativeQuery = true)
    List<OrderEntity> findByParty(Long id);

    @Query(value = "SELECT name as name, COUNT(*) as count, sum(price) as price FROM orders WHERE person_id = ?2 AND party_id = ?1 GROUP BY name", nativeQuery = true)
    List<GroupedOrderItemEntity> findGroupedOrder(Long party_id, Long person_id);

    @Query(value = "CALL add_item_to_order(?1, ?2, ?3, 1)", nativeQuery = true)
    Long addOrder(Long party_id, Long cocktail_id, Long person_id);
}
