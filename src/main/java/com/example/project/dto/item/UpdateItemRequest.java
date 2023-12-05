package com.example.project.dto.item;

import com.example.project.domain.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UpdateItemRequest {

    private String name;
    private int price;
    private String picture;
    private String content;
    private ItemStatus status;
}
