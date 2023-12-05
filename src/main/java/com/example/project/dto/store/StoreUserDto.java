package com.example.project.dto.store;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class StoreUserDto {

    private Long storeId;
    private String storeName;
    private String content;
    private Long userId;
    private String userName;

    @QueryProjection
    public StoreUserDto(Long storeId, String storeName, String content, Long userId, String userName) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
    }
}
