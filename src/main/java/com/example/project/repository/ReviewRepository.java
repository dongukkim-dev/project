package com.example.project.repository;

import com.example.project.domain.Review;
import com.example.project.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByStore(Store store);
}
