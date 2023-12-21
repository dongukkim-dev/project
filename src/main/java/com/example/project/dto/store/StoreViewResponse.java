package com.example.project.dto.store;

import com.example.project.domain.Article;
import com.example.project.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoreViewResponse {

    private Long id;
    private String name;
    private String boss;
    private String content;
    private LocalDateTime createdDate;

    public StoreViewResponse(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.boss = store.getUser().getName();
        this.content = store.getContent();
        this.createdDate = store.getCreatedDate();
    }
}
