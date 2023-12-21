package com.example.project.dto.order;

import com.example.project.domain.Order;
import com.example.project.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 메뉴 개수, 메뉴 목록, 배달 주소, 주문 시간이 나와야 한다.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long order_id;
    private String name; //주문자 이름
    private String address;
    private String detail;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<OrderItemDto> orderItems;

    public OrderResponse(Order order) {
        this.order_id = order.getId();
        this.name = order.getUser().getName();
        this.address = order.getUser().getAddress();
        this.detail = order.getUser().getDetail();
        this.orderStatus = order.getStatus();
        this.orderDate = order.getCreatedDate();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
    }
}
