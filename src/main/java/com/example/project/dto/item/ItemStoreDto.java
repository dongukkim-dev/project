package com.example.project.dto.item;

import com.example.project.domain.ItemStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ItemStoreDto {

    private Long itemId;
    private String itemName;
    private int price;
    private ItemStatus status;
    private Long storeId;
    private String storeName;

    @QueryProjection
    public ItemStoreDto(Long itemId, String itemName, int price, ItemStatus status, Long storeId, String storeName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.status = status;
        this.storeId = storeId;
        this.storeName = storeName;
    }
}
