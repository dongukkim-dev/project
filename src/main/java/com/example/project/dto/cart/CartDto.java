package com.example.project.dto.cart;

import lombok.*;

/**
 * /api/cart post 요청으로 들어오는 @RequestBody
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long item_id;
    private int amount;
}
