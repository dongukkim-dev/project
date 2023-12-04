package com.example.project.controller;

import com.example.project.domain.Store;
import com.example.project.dto.store.AddStoreRequest;
import com.example.project.dto.store.StoreResponse;
import com.example.project.dto.store.UpdateStoreRequest;
import com.example.project.service.ReviewService;
import com.example.project.service.StoreService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreService storeService;

    @PostMapping("/api/stores")
    public ResponseEntity<Store> addStore(@RequestBody AddStoreRequest request) {

        String email = SecurityUtil.getCurrentUsername();
        Store savedStore = storeService.save(request, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedStore);
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

    @GetMapping("/api/stores/{id}")
    public ResponseEntity<StoreResponse> findStore(@PathVariable long id) {
        Store store = storeService.findById(id);

        return ResponseEntity.ok()
                .body(new StoreResponse(store));
    }

    @DeleteMapping("/api/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable long id) {
        storeService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/stores/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable long id,
                                                 @RequestBody UpdateStoreRequest request) {
        Store updatedStore = storeService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedStore);
    }
}
