package com.example.project.controller;

import com.example.project.domain.Item;
import com.example.project.dto.cart.CartDto;
import com.example.project.dto.cart.CartResponse;
import com.example.project.dto.item.*;
import com.example.project.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    //store_id 를 받아서 해당 store의 메뉴 추가
    @PostMapping(value = "/api/items/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ItemResponse> addItem(
            @PathVariable long id,
            @RequestPart(value = "item", required = false) AddItemRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Item savedItem = itemService.save(id, file, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ItemResponse(savedItem));
    }

    @GetMapping("/api/items")
    public ResponseEntity<List<ItemResponse>> findAllItems() {
        List<ItemResponse> items = itemService.findAll()
                .stream()
                .map(ItemResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(items);
    }

    //음식점 id에 따른 상품 목록들 전체 조회
    @GetMapping("/api/items/{id}")
    public ResponseEntity<Page<ItemStoreDto>> findItem(@PathVariable long id, ItemSearchCondition condition, Pageable pageable) {
        Page<ItemStoreDto> items = itemService.searchItem(id, condition, pageable);

        return ResponseEntity.ok()
                .body(items);
    }

    //음식 상세정보 조회
    @GetMapping("/api/item/{itemId}")
    public ResponseEntity<ItemResponse> findDetailItem(@PathVariable long itemId) {
        Item item = itemService.findById(itemId);

        return ResponseEntity.ok()
                .body(new ItemResponse(item));
    }

    @DeleteMapping("/api/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        itemService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping(value = "/api/items/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable long id,
            @RequestPart(value = "item") UpdateItemRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        log.info("item = {}, {}, {}", request.getItemStatus(), request.getItemStatus(), request.getContent());

        Item updatedItem = itemService.update(id, file, request);

        return ResponseEntity.ok()
                .body(new ItemResponse(updatedItem));
    }

    /**
     * 장바구니 관련 코드
     */
    //장바구니에서 [itemId, amount] json 데이터를 받아 필요한 값들 [itemName, amount, price] 반환 하기
    @PostMapping("/api/cart")
    public ResponseEntity<List<CartResponse>> cartItem(@RequestBody List<CartDto> cartDto) {

        List<CartResponse> cartItems = itemService.findCartItems(cartDto);

        return ResponseEntity.ok()
                .body(cartItems);
    }
}
