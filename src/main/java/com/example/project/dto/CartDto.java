package com.example.project.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long item_id;
    private int amount;
}
