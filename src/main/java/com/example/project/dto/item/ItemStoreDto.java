package com.example.project.dto.item;

import com.example.project.domain.ItemStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ItemStoreDto {

    private Long itemId;
    private String itemName;
    private String content;
    private int price;
    private String picture;
    private ItemStatus itemStatus;
    private boolean deleted;
    private Long storeId;
    private String storeName;

    @QueryProjection
    public ItemStoreDto(Long itemId, String itemName, String content, int price, String picture, ItemStatus status, boolean deleted, Long storeId, String storeName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.content = content;
        this.price = price;
        this.picture = picture;
        this.itemStatus = status;
        this.deleted = deleted;
        this.storeId = storeId;
        this.storeName = storeName;
    }
}
