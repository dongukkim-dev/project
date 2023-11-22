package com.example.project.dto.order;

import com.example.project.domain.Order;
import com.example.project.domain.User;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    //장바구니에서 넘어오는 정보들?
    private Long orderId;
    private User user;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        orderId = order.getId();
        user = order.getUser();
//        orderItems = order.
    }
}
