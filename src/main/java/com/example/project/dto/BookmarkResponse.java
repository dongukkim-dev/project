package com.example.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BookmarkResponse {

    private Long bookmarkId;
    private Long storeId;
    private String storeName;
    //필요하면 rating 등 추가

    @QueryProjection
    public BookmarkResponse(Long bookmarkId, Long storeId, String storeName) {
        this.bookmarkId = bookmarkId;
        this.storeId = storeId;
        this.storeName = storeName;
    }
}
