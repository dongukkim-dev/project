package com.example.project.dto.order;

import com.example.project.domain.Order;
import com.example.project.domain.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDataDto {

    private Long orderId;
    private String name; //주문자 이름
    private String address;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime completeDate;
    private List<OrderItemDto> orderItems;
    private Integer totalPrice;

    public OrderDataDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getUser().getName();
        this.address = order.getUser().getAddress();
        this.orderStatus = order.getStatus();
        this.orderDate = order.getCreatedDate();
        this.completeDate = order.getLastModifiedDate();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
    }

    @QueryProjection
    public OrderDataDto(Long orderId, String name, String address, OrderStatus orderStatus, LocalDateTime orderDate, LocalDateTime completeDate, List<OrderItemDto> orderItems, Integer totalPrice) {
        this.orderId = orderId;
        this.name = name;
        this.address = address;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.completeDate = completeDate;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }
}
