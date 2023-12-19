package com.example.project.service;

import com.example.project.config.fileupload.FileUpload;
import com.example.project.domain.Order;
import com.example.project.domain.Review;
import com.example.project.domain.Store;
import com.example.project.dto.review.AddReviewRequest;
import com.example.project.dto.review.ReviewResponse;
import com.example.project.dto.review.UpdateReviewRequest;
import com.example.project.repository.ReviewRepository;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderService orderService;
    private final StoreService storeService;
    private final FileUpload fileUpload;

    @Transactional
    public Review save(long id, MultipartFile file, AddReviewRequest request) {
        Order order = orderService.findById(id);

        if (file == null) {
            request.setPicture("");
        }
        else {
            if (!fileUpload.uploadReviewImg(request, file))
                throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        return reviewRepository.save(request.toEntity(order.getStore(), order.getUser(), order));
    }

    //update 코드
    @Transactional
    public Review update(long id, MultipartFile file, UpdateReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeReviewAuthor(review);

        if (file == null) {
            request.setPicture("");
        }
        else {
            if (!fileUpload.uploadReviewImg(request, file))
                throw new IllegalArgumentException("업로드할 파일이 없습니다.");
        }

        review.update(request.getContent(), request.getRating(), request.getPicture());

        return review;
    }

    @Transactional
    public void delete(long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeReviewAuthor(review);
        reviewRepository.delete(review);
    }

    public List<Review> findByStore(long id) {

        Store store = storeService.findById(id);

        return reviewRepository.findByStore(store);
    }

    //리뷰를 추가한 유저인지 확인
    private static void authorizeReviewAuthor(Review review) {
        String email = SecurityUtil.getCurrentUsername();
        if (!review.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
