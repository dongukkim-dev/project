package com.example.project.dto.item;

import com.example.project.domain.Item;
import com.example.project.domain.ItemStatus;
import lombok.Getter;

@Getter
public class ItemResponse {

    private final Long itemId;
    private final String itemName;
    private final int price;
    private final String picture;
    private final ItemStatus itemStatus;

    public ItemResponse(Item item) {
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.price = item.getPrice();
        this.picture = item.getPicture();
        this.itemStatus = item.getStatus();
    }
}
