package com.example.project.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreRequest {

    private String name;
    private String picture;
    private String content;
    private float rating;
}
