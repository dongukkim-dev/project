package com.example.project.dto.item;

import com.example.project.domain.Item;
import lombok.Getter;

@Getter
public class ItemListViewResponse {

    private final String name;
    private final int price;
    private final String picture;
    private final String content;

    public ItemListViewResponse(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.picture = item.getPicture();
        this.content = item.getContent();
    }
}
