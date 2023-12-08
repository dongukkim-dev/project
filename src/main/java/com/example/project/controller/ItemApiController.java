package com.example.project.controller;

import com.example.project.domain.Item;
import com.example.project.dto.cart.CartDto;
import com.example.project.dto.cart.CartResponse;
import com.example.project.dto.item.*;
import com.example.project.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    //storeName을 이대로 받을 수 있나? pathvariable 방식으로 받을수 있나
    @PostMapping("/api/items/{id}")
    public ResponseEntity<ItemResponse> addItem(@PathVariable long id, @RequestBody AddItemRequest request) {
        Item savedItem = itemService.save(id, request);

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
    @DeleteMapping("/api/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        itemService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/items/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable long id,
                                                 @RequestBody UpdateItemRequest request) {
        Item updatedItem = itemService.update(id, request);

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
