package com.example.project.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 프론트에서 보여줄 장바구니 정보 DTO
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private Long item_id;
    private String name;
    private int price;
    private int amount;
}
