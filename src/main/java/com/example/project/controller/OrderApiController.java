package com.example.project.controller;

import com.example.project.domain.Order;
import com.example.project.dto.order.OrderDto;
import com.example.project.dto.order.OrderRequest;
import com.example.project.service.OrderService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<List<OrderRequest>> addOrder(@RequestBody List<OrderRequest> request) {

        String email = SecurityUtil.getCurrentUsername();
        Order savedOrder = orderService.addOrder(request, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(request);
    }

    //음식점에서 들어온 주문 정보들 조회
    @GetMapping("/api/orders/{id}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable("id") long store_id) {
        List<Order> orders = orderService.findAllByStore(store_id);

        return ResponseEntity.ok()
                .body(orders);
    }

    @DeleteMapping("/api/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        orderService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
