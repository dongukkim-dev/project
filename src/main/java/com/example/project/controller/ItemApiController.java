package com.example.project.controller;

import com.example.project.domain.Item;
import com.example.project.domain.Store;
import com.example.project.dto.item.AddItemRequest;
import com.example.project.dto.item.ItemResponse;
import com.example.project.dto.item.UpdateItemRequest;
import com.example.project.dto.store.AddStoreRequest;
import com.example.project.dto.store.StoreResponse;
import com.example.project.dto.store.UpdateStoreRequest;
import com.example.project.service.ItemService;
import com.example.project.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    //principal 에는 아마 이름밖에 없던걸로 아는데
//    @PostMapping("/api/items")
//    public ResponseEntity<Item> addItem(@RequestBody AddItemRequest request, Principal principal) {
//        Item savedItem = itemService.save(request, principal.getName());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(savedItem);
//    }

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
    public ResponseEntity<ItemResponse> findItem(@PathVariable long id) {
        Item item = itemService.findById(id);

        return ResponseEntity.ok()
                .body(new ItemResponse(item));
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
