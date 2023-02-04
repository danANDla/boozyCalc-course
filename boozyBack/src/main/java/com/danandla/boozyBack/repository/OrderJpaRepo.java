package com.danandla.boozyBack.repository;

import com.danandla.boozyBack.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface OrderJpaRepo extends JpaRepository<OrderEntity, Long> {

    @Procedure(procedureName = "add_item_to_order", outputParameterName = "order_id")
//    Long addOrder(Long party_id, Long cocktail_id, Long person_id, Long order_id);
    Long addOrder(@Param("p_id") Long party_id, @Param("p_id") Long cocktail_id, @Param("p_id") Long person_id, @Param("order_id") Long order_id);
}
