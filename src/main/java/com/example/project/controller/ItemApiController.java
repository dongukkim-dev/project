package com.example.project.controller;

import com.example.project.domain.Item;
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
    @PostMapping("/api/items")
    public ResponseEntity<ItemResponse> addItem(@RequestBody AddItemRequest request) {
        Item savedItem = itemService.save(request);

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
    public ResponseEntity<Item> updateItem(@PathVariable long id,
                                                 @RequestBody UpdateItemRequest request) {
        Item updatedItem = itemService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedItem);
    }
}
