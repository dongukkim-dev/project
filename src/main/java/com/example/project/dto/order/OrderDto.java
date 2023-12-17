package com.example.project.dto.order;

import com.example.project.domain.Order;
import com.example.project.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 임시로 일반 회원의 주문 내역에 들어갈 정보들만 만들어보자
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderDate = order.getCreatedDate();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
    }
}
