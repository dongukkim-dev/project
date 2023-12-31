package com.example.project.dto.store;

import com.example.project.domain.Article;
import com.example.project.domain.Store;
import lombok.Getter;

/**
 * 음식점의 상세정보를 받는 dto
 */

@Getter
public class StoreResponse {

    private final Long id;
    private final String name;
    private final String picture;
    private final String content;
    private final String phone;
    private final String address;
    private final String detail;
    private final String openTime;
    private final String closeTime;
    private final Integer minOrderPrice;
    private final Integer count;

    public StoreResponse(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.picture = store.getPicture();
        this.content = store.getContent();
        this.phone = store.getPhone();
        this.address = store.getAddress();
        this.detail = store.getDetail();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
        this.count = store.getBookmarks().size();
    }
}
