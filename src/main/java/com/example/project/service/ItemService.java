package com.example.project.service;

import com.example.project.domain.Item;
import com.example.project.domain.Store;
import com.example.project.dto.cart.CartDto;
import com.example.project.dto.cart.CartResponse;
import com.example.project.dto.item.*;
import com.example.project.repository.item.ItemQueryRepository;
import com.example.project.repository.item.ItemRepository;
import com.example.project.repository.store.StoreQueryRepository;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final StoreService storeService; //service가 service를 의존하는건 아닌거 같은데 현재 service가 findByName 같은 간단한 작업만 해서 그냥 사용
    private final ItemQueryRepository itemQueryRepository;

    public Item save(long id, AddItemRequest request) {
        Store store = storeService.findById(id);
        return itemRepository.save(request.toEntity(store));
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    //장바구니에 itemId, itemName, price, amount만 반환할 코드
    public List<CartResponse> findCartItems(List<CartDto> cartDtos) {

        return cartDtos.stream()
                .map(cart -> itemQueryRepository.searchCartData(cart.getItemId(), cart.getAmount()))
                .collect(Collectors.toList());

    }

    @Transactional
    public Item update(long id, UpdateItemRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeItemAuthor(item);
        item.update(request.getName(), request.getPrice(), request.getPicture(), request.getContent(), request.getStatus());

        return item;
    }

    @Transactional
    public void delete(long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeItemAuthor(item);
        item.deletedChange();
    }

    //아이템 목록 페이징 처리
    public Page<ItemStoreDto> searchItem(long id, ItemSearchCondition condition, Pageable pageable) {
        return itemQueryRepository.search(id, condition, pageable);
    }

    //아이템을 추가한 유저인지 확인
    private static void authorizeItemAuthor(Item item) {
        String email = SecurityUtil.getCurrentUsername();
        if (!item.getStore().getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
