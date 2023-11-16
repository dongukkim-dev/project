package com.example.project.dto.item;

import com.example.project.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemViewResponse {

    private String name;
    private int price;
    private String picture;
    private String content;
    private int stockQuantity;

    public ItemViewResponse(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
        this.picture = item.getPicture();
        this.content = item.getContent();
        this.stockQuantity = item.getStockQuantity();
    }
}
