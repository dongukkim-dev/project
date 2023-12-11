package com.example.project.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BookmarkStatusResponse {

    private boolean status;
    private Integer count;

    @QueryProjection
    public BookmarkStatusResponse(boolean status, Integer count) {
        this.status = status;
        this.count = count;
    }
}
