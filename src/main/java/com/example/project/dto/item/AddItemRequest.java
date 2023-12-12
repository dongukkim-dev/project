package com.example.project.dto.item;

import com.example.project.domain.Item;
import com.example.project.domain.ItemStatus;
import com.example.project.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {

    private String name;
    private int price;
    private String picture;
    private String content;

    public Item toEntity(Store store) {
        return Item.builder()
                .name(name)
                .price(price)
                .picture(picture)
                .content(content)
                .store(store)
                .status(ItemStatus.SALE)
                .build();
    }
}
