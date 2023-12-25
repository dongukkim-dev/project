package com.example.project.dto.store;

import com.example.project.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreSimpleInfoResponse {

    private Long id;
    private String name;
    private String picture;
    private String category;
    private double rating;
}
