package com.example.project.service;

import com.example.project.domain.Item;
import com.example.project.domain.Order;
import com.example.project.domain.Review;
import com.example.project.dto.review.AddReviewRequest;
import com.example.project.dto.review.UpdateReviewRequest;
import com.example.project.repository.ReviewRepository;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderService orderService;

    public Review save(AddReviewRequest request) {
        Order order = orderService.findById(request.getOrderId());

        return reviewRepository.save(request.toEntity(order.getStore(), order.getUser()));
    }

    //update 코드
    @Transactional
    public Review update(long id, UpdateReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeItemAuthor(review);
        review.update(request.getTitle(), request.getContent(), request.getRating(), request.getPicture());

        return review;
    }

    @Transactional
    public void delete(long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeItemAuthor(review);
        review.deletedChange();
    }

    //리뷰를 추가한 유저인지 확인
    private static void authorizeItemAuthor(Review review) {
        String email = SecurityUtil.getCurrentUsername();
        if (!review.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
