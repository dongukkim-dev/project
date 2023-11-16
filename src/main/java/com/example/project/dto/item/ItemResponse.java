package com.example.project.dto.item;

import com.example.project.domain.Item;
import lombok.Getter;

@Getter
public class ItemResponse {

    private final String name;
    private final int price;

    public ItemResponse(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
    }
}
