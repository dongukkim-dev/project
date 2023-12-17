package com.example.project.dto.item;

import com.example.project.domain.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequest {

    private String itemName;
    private int price;
    private String picture;
    private String content;
    private String itemStatus;
}
