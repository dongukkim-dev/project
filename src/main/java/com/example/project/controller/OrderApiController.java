package com.example.project.controller;

import com.example.project.domain.Order;
import com.example.project.dto.order.OrderDto;
import com.example.project.dto.order.OrderRequest;
import com.example.project.dto.order.OrderResponse;
import com.example.project.service.OrderService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @PostMapping("/api/orders")
    public ResponseEntity<List<OrderRequest>> addOrder(@RequestBody List<OrderRequest> request) {

        String email = SecurityUtil.getCurrentUsername();
        Order savedOrder = orderService.addOrder(request, email);

        // SSE를 통해 새로운 주문을 클라이언트에게 알리기
        sendOrderUpdate();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(request);
    }

    //음식점에서 들어온 주문 정보들 조회
//    @GetMapping(value = "/api/orders/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable("id") long store_id) {
//        List<Order> orders = orderService.findAllByStore(store_id);
//
//        List<OrderResponse> orderResponse = orders.stream()
//                .map(OrderResponse::new)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok()
//                .body(orderResponse);
//    }

    @GetMapping(value = "/api/orders/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> getOrders(@PathVariable("id") long store_id) {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        // 클라이언트에게 현재 주문 목록을 전송
        sendCurrentOrders(emitter, store_id);

        return ResponseEntity.ok()
                .body(emitter);
    }

    @DeleteMapping("/api/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        orderService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    private void sendOrderUpdate() {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().data("Order updated!").name("orderUpdate"));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
                e.printStackTrace();
            }
        });
    }

    private void sendCurrentOrders(SseEmitter emitter, long storeId) {
        List<Order> orders = orderService.findAllByStore(storeId);

        orders.forEach(order -> {
            try {
                emitter.send(SseEmitter.event().data(new OrderResponse(order)).name("order"));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
                e.printStackTrace();
            }
        });
    }
}
