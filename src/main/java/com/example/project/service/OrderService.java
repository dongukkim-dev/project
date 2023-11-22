package com.example.project.service;

import com.example.project.domain.OrderItem;
import com.example.project.dto.order.OrderRequest;
import com.example.project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 주문
     */
//    @Transactional
//    public Long order(OrderRequest request) {
//
//        List<OrderItem> orderItems
//    }
}
