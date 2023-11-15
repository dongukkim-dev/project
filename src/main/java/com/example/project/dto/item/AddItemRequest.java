package com.example.project.dto.item;

import com.example.project.domain.Item;
import com.example.project.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddItemRequest {

    private String name;
    private int price;
    private String picture;
    private String content;
    private int stockQuantity;

    public Item toEntity(Store store) {
        return Item.builder()
                .name(name)
                .price(price)
                .picture(picture)
                .content(content)
                .stockQuantity(stockQuantity)
                .store(store)
                .build();
    }
}
