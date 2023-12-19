package com.example.project.dto.cart;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 프론트에서 보여줄 장바구니 정보 DTO
 */

@Getter @Setter
@NoArgsConstructor
public class CartResponse {

    private Long item_id;
    private String name;
    private String picture;
    private int price;
    private int amount;

    @QueryProjection
    public CartResponse(Long item_id, String name, String picture, int price, int amount) {
        this.item_id = item_id;
        this.name = name;
        this.picture = picture;
        this.price = price;
        this.amount = amount;
    }
}
