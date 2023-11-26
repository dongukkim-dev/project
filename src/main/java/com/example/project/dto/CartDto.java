package com.example.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CartDto {

    private Long item_id;
    private int amount;

    @Builder
    public CartDto(Long item_id, int amount) {
        this.item_id = item_id;
        this.amount = amount;
    }
}
