package com.example.project.service;

import com.example.project.domain.Item;
import com.example.project.domain.Store;
import com.example.project.dto.CartResponse;
import com.example.project.dto.item.AddItemRequest;
import com.example.project.dto.item.ItemViewResponse;
import com.example.project.dto.item.UpdateItemRequest;
import com.example.project.repository.ItemRepository;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final StoreService storeService; //service가 service를 의존하는건 아닌거 같은데 현재 service가 findByName 같은 간단한 작업만 해서 그냥 사용

    public Item save(AddItemRequest request) {
        Store store = storeService.findById(request.getStoreId());
        return itemRepository.save(request.toEntity(store));
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    //장바구니에 item_id, price만 반환할 코드
    public CartResponse findCartById(long id, int amount) {
        return itemRepository.findByNameAndPrice(id, amount);
    }

    public void delete(long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeStoreAuthor(item);
        itemRepository.delete(item);
    }

    @Transactional
    public Item update(long id, UpdateItemRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeStoreAuthor(item);
        item.update(request.getName(), request.getPrice(), request.getPicture(), request.getContent());

        return item;
    }

    public List<ItemViewResponse> findAllDesc() {
        return itemRepository.findAllDesc().stream()
                .map(ItemViewResponse::new)
                .collect(Collectors.toList());
    }

    //음식점을 추가한 유저인지 확인
    private static void authorizeStoreAuthor(Item item) {
        String email = SecurityUtil.getCurrentUsername();
        if (!item.getStore().getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
