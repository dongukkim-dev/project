package com.example.project.repository;

import com.example.project.domain.Store;
import com.example.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("select s from Store s order by s.id desc")
    List<Store> findAllDesc();

    Optional<Store> findByName(String name);
    Optional<Store> findByUser(User user);
}
