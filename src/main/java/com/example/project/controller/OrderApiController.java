package com.example.project.controller;

import com.example.project.domain.Order;
import com.example.project.dto.order.OrderDto;
import com.example.project.dto.order.OrderRequest;
import com.example.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<List<OrderRequest>> addOrder(@RequestBody List<OrderRequest> request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Order savedOrder = orderService.order(request, email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(request);
    }
}
