package com.example.project.dto.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@Data
public class OrderDto {

    private List<OrderDataDto> orders;
    private List<OrderDataDto> processingOrders;
    private List<OrderDataDto> cancelOrders;
    private List<OrderDataDto> compOrders;

    @QueryProjection
    public OrderDto(List<OrderDataDto> orders, List<OrderDataDto> processingOrders, List<OrderDataDto> cancelOrders, List<OrderDataDto> compOrders) {
        this.orders = orders;
        this.processingOrders = processingOrders;
        this.cancelOrders = cancelOrders;
        this.compOrders = compOrders;
    }
}
