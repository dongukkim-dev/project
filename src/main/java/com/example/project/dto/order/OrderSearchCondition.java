package com.example.project.dto.order;

import lombok.Data;

@Data
public class OrderSearchCondition {
    //[검색조건] store_id, userId, order_status

    private Long storeId;
    private Long userId;
    private String status;
}
