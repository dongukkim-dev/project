package com.example.project.repository;

import com.example.project.domain.Review;
import com.example.project.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findByStore(Store store);
}
