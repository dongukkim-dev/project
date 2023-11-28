package com.example.project.dto.order;

import com.example.project.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 장바구니에 담긴 아이템들을 주문 하면 정보를 받는 DTO
 *
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long item_id;
    private int amount;
}
