package com.example.project.dto.store;

import com.example.project.domain.Article;
import com.example.project.domain.Store;
import lombok.Getter;

@Getter
public class StoreListViewResponse {

    private final Long id;
    private final String name;
    private final String boss;
    private final String picture;
    private final String content;
    private final double rating;

    public StoreListViewResponse(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.boss = store.getBoss();
        this.picture = store.getPicture();
        this.content = store.getContent();
        this.rating = store.getRating();
    }
}
