package com.example.project.dto.store;

import com.example.project.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddStoreRequest {

    private String name;
    private String picture;
    private String content;
    private float rating;

    public Store toEntity(String boss) {
        return Store.builder()
                .name(name)
                .boss(boss)
                .picture(picture)
                .content(content)
                .rating(rating)
                .build();
    }
}
