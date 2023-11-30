package com.example.project.repository;

import com.example.project.domain.Item;
import com.example.project.dto.CartResponse;
import com.example.project.dto.item.ItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i order by i.id desc")
    List<Item> findAllDesc();

    @Query("select new com.example.project.dto.CartResponse(i.id, i.name, i.price, :amount) from Item i where i.id= :id")
    CartResponse findByNameAndPrice(@Param("id") long id, @Param("amount") int amount);
}
