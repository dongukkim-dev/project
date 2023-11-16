package com.example.project.dto.store;

import com.example.project.domain.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "음식점 추가 request")
public class AddStoreRequest {

    @Schema(description = "음식점 이름")
    private String name;

    @Schema(description = "음식점 대표 사진 url")
    private String picture;

    @Schema(description = "음식점 상세 설명")
    private String content;

    @Schema(description = "음식점 평점")
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
