package com.example.project.repository;

import com.example.project.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.store.id= :store_id")
    List<Order> findAllByStore(@Param("store_id") long store_id);

    @Query("select o from Order o where o.store.id= :store_id and o.id= :order_id")
    Order findLastOrderByStore(@Param("store_id") long store_id, @Param("order_id") long order_id);
}
