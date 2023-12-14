package com.example.project.controller;

import com.example.project.domain.Bookmark;
import com.example.project.domain.Review;
import com.example.project.domain.Store;
import com.example.project.dto.BookmarkResponse;
import com.example.project.dto.BookmarkStatusResponse;
import com.example.project.dto.review.AddReviewRequest;
import com.example.project.dto.review.ReviewResponse;
import com.example.project.dto.review.UpdateReviewRequest;
import com.example.project.dto.store.StoreSearchCondition;
import com.example.project.dto.store.AddStoreRequest;
import com.example.project.dto.store.StoreResponse;
import com.example.project.dto.store.StoreUserDto;
import com.example.project.dto.store.UpdateStoreRequest;
import com.example.project.repository.store.StoreQueryRepository;
import com.example.project.repository.store.StoreRepository;
import com.example.project.service.BookmarkService;
import com.example.project.service.ReviewService;
import com.example.project.service.StoreService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;
    private final ReviewService reviewService;
    private final BookmarkService bookmarkService;
    private final StoreQueryRepository storeQueryRepository;

    @PostMapping("/api/store")
    public ResponseEntity<Store> addStore(@RequestBody AddStoreRequest request) {

        String email = SecurityUtil.getCurrentUsername();
        Store savedStore = storeService.save(request, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedStore);
    }

    //스프링 데이터와 페이징의 조합
    @GetMapping("/api/storesTest")
    public Page<StoreUserDto> searchStore(StoreSearchCondition condition, Pageable pageable) {
        return storeRepository.searchComplex(condition, pageable);
    }

    @GetMapping("/api/stores")
    public ResponseEntity<List<StoreResponse>> findAllStores() {
        List<StoreResponse> stores = storeService.findAll()
                .stream()
                .map(StoreResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(stores);
    }

    //category를 이용한 음식점 찾기
//    @GetMapping("/api/stores/{category}")
//    public String store(@PathVariable long category) {
//        storeService.findByCategory();
//    }

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

    @PutMapping("/api/store/{id}")
    public ResponseEntity<StoreResponse> updateStore(@PathVariable long id,
                                                 @RequestBody UpdateStoreRequest request) {
        Store updatedStore = storeService.update(id, request);

        return ResponseEntity.ok()
                .body(new StoreResponse(updatedStore));
    }

    /**
     * 리뷰 관련 코드
     */
    @PostMapping("/api/review")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody AddReviewRequest request) {
        Review savedReview = reviewService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReviewResponse(savedReview));
    }

    @PutMapping("/api/review/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable long id, @RequestBody UpdateReviewRequest request) {
        Review updatedReview = reviewService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedReview);
    }

    //order_id를 받아서 해당 주문에 대한 리뷰를 삭제
    @DeleteMapping("/api/review/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id) {


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
