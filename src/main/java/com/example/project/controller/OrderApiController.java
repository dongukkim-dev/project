package com.example.project.controller;

import com.example.project.domain.Order;
import com.example.project.dto.order.OrderDto;
import com.example.project.dto.order.OrderRequest;
import com.example.project.dto.order.OrderResponse;
import com.example.project.service.OrderService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final SseEmitters sseEmitters;

    @PostMapping("/api/orders")
    public ResponseEntity<List<OrderRequest>> addOrder(@RequestBody List<OrderRequest> request) {

        String email = SecurityUtil.getCurrentUsername();
        Order savedOrder = orderService.addOrder(request, email);

        //마지막으로 추가된 주문의 ID 갱신
        log.info("saveOrder 데이터 존재 여부 orderSize = {}, order_id = {}", savedOrder.getOrderItems().size(), savedOrder.getId());

        // SSE를 통해 새로운 주문을 클라이언트에게 알리기
        sseEmitters.order(savedOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(request);
    }

//    @GetMapping(value = "/api/orders/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public ResponseEntity<SseEmitter> getOrders(@PathVariable("id") long store_id) {
//        SseEmitter emitter = new SseEmitter(60 * 1000L);
//        sseEmitters.add(emitter);
//
//        try {
//            //여기서 마지막으로 추가된 주문만 받아와야 함
//            Order order = orderService.findLastOrderByStore(store_id, lastOrderId);
//            log.info("order 값: {}", order);
//
//            if (order != null) {
//                try {
//                    emitter.send(SseEmitter.event()
//                            .data(new OrderResponse(order))
//                            .name("order"));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        } catch (Exception e) {
//            // 다른 예외 처리 코드
//            throw new RuntimeException(e);
//        }
//
//        return ResponseEntity.ok()
//                .body(emitter);
//    }

    @DeleteMapping("/api/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        orderService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    /**
     * sse connect 함수
     */
    @GetMapping(value = "/api/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        SseEmitter emitter = new SseEmitter(60 * 1000L * 60);
        sseEmitters.add(emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }
}
