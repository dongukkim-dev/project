package com.example.project.controller;

import com.example.project.domain.Bookmark;
import com.example.project.domain.Review;
import com.example.project.domain.Store;
import com.example.project.dto.BookmarkResponse;
import com.example.project.dto.BookmarkStatusResponse;
import com.example.project.dto.review.AddReviewRequest;
import com.example.project.dto.review.ReviewResponse;
import com.example.project.dto.review.UpdateReviewRequest;
import com.example.project.dto.store.AddStoreRequest;
import com.example.project.dto.store.StoreResponse;
import com.example.project.dto.store.StoreSimpleInfoResponse;
import com.example.project.dto.store.UpdateStoreRequest;
import com.example.project.repository.store.StoreQueryRepository;
import com.example.project.repository.store.StoreRepository;
import com.example.project.service.BookmarkService;
import com.example.project.service.ReviewService;
import com.example.project.service.StoreService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreService storeService;
    private final ReviewService reviewService;
    private final BookmarkService bookmarkService;
    private final StoreQueryRepository storeQueryRepository;

    @PostMapping(value = "/api/store", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Store> addStore(
            @RequestPart(value = "store", required = false) AddStoreRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        log.info("store = {}, file = {}", request.getName(), file);

        String email = SecurityUtil.getCurrentUsername();
        Store savedStore = storeService.save(request, file, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedStore);
    }

    @GetMapping("/api/stores")
    public ResponseEntity<List<StoreSimpleInfoResponse>> findStoreSimpleInfo() {

        return ResponseEntity.ok()
                .body(storeQueryRepository.searchMainInfo());
    }

    @GetMapping("/api/store/{id}")
    public ResponseEntity<StoreResponse> findStore(@PathVariable long id) {
        Store store = storeService.findById(id);

        return ResponseEntity.ok()
                .body(new StoreResponse(store));
    }

    @DeleteMapping("/api/store/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable long id) {
        storeService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping(value = "/api/store/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<StoreResponse> updateStore(
            @PathVariable long id,
            @RequestPart(value = "store") UpdateStoreRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Store updatedStore = storeService.update(id, file, request);

        return ResponseEntity.ok()
                .body(new StoreResponse(updatedStore));
    }

    /**
     * 리뷰 관련 코드
     */
    @PostMapping(value = "/api/review/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable("orderId") long id,
            @RequestPart(value = "review") AddReviewRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Review savedReview = reviewService.save(id, file, request);

        log.info("review 값 : {}", savedReview.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReviewResponse(savedReview));
    }

    //해당 음식점에 대한 리뷰들 가져오기
    @GetMapping("/api/review/{storeId}")
    public ResponseEntity<List<ReviewResponse>> findReviews(@PathVariable("storeId") long id) {

        List<ReviewResponse> reviews = reviewService.findByStore(id)
                .stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(reviews);
    }

    @PutMapping(value = "/api/review/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Review> updateReview(
            @PathVariable long id,
            @RequestPart(value = "review") UpdateReviewRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Review updatedReview = reviewService.update(id, file, request);

        return ResponseEntity.ok()
                .body(updatedReview);
    }

    //order_id를 받아서 해당 주문에 대한 리뷰를 삭제
    @DeleteMapping("/api/review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id) {
        reviewService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    /**
     * 찜하기 관련 코드
     */
    //store_id 를 가져와서 저장해야 한다.
    @PostMapping("/api/bookmark/{id}")
    public ResponseEntity<String> addBookMark(@PathVariable long id) {
        String email = SecurityUtil.getCurrentUsername();
        log.info("email = {}", email);

        Bookmark bookmark = bookmarkService.addBookmark(id, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("완료");
    }

    @GetMapping("/api/bookmark")
    public ResponseEntity<List<BookmarkResponse>> findBookmark() {
        String email = SecurityUtil.getCurrentUsername();
        log.info("찜목록 전체 불러오기 email = {}", email);

        List<BookmarkResponse> bookmark = storeQueryRepository.searchBookmark(email);

        return ResponseEntity.ok()
                .body(bookmark);
    }

    //store_id를 이용해 이용 유저가 해당 store를 찜했는지 안했는지 true/false 와 총 찜한 인원 수 반환
    @GetMapping("/api/bookmark/{id}")
    public ResponseEntity<BookmarkStatusResponse> bookmarkStatus(@PathVariable long id) {
        String email = SecurityUtil.getCurrentUsername();
        log.info("음식점 상세정보에서의 유저 email = {}", email);

        BookmarkStatusResponse bookmarkStatus = bookmarkService.bookmarkStatus(id, email);

        return ResponseEntity.ok()
                .body(bookmarkStatus);
    }

    @DeleteMapping("/api/bookmark/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable long id) {
        String email = SecurityUtil.getCurrentUsername();
        log.info("북마크 삭제에서의 유저 email = {}", email);

        bookmarkService.deleteBookmark(id, email);

        return ResponseEntity.ok()
                .build();
    }
}
