package com.example.project.dto.store;

import com.example.project.domain.Article;
import com.example.project.domain.Store;
import lombok.Getter;

@Getter
public class StoreResponse {

    private final String name;
    private final String picture;
    private final String content;

    public StoreResponse(Store store) {
        this.name = store.getName();
        this.picture = store.getPicture();
        this.content = store.getContent();
    }
}
