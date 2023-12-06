package com.example.project.dto.item;

import com.example.project.domain.ItemStatus;
import lombok.Data;

@Data
public class ItemSearchCondition {
    //[검색조건] 상품 이름, 가격, 음식점 이름, 판매 상태

    private String itemName;
    private Integer priceGoe;
    private Integer priceLoe;
    private String storeName;
    private String status;
}
