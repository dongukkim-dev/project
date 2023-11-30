package com.example.project.service;

import com.example.project.domain.Review;
import com.example.project.dto.review.AddReviewRequest;
import com.example.project.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

//    public Review save(AddReviewRequest request) {
//        reviewRepository.save(request)
//    }
}
