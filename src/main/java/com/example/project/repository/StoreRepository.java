package com.example.project.repository;

import com.example.project.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("select s from Store s order by s.id desc")
    List<Store> findAllDesc();
}
