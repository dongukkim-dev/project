package com.example.project.dto.store;

import com.example.project.domain.Store;
import com.example.project.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private double rating; //임시 테스트 용
//    private String email; //임시 테스트 용

    public Store toEntity(User user) {
        return Store.builder()
                .name(name)
                .user(user)
                .picture(picture)
                .content(content)
                .rating(rating)
                .build();
    }
}
