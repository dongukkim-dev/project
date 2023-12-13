package com.example.project.dto.order;

import lombok.Data;

@Data
public class OrderSearchCondition {
    //[검색조건] store_id, order_status, 유저 정보

    private Long storeId;
    private String status;
    private String email;
}
