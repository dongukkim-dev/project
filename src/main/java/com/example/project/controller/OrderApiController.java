package com.example.project.controller;

import com.example.project.domain.Order;
import com.example.project.dto.SaleDto;
import com.example.project.dto.SalesDto;
import com.example.project.dto.order.OrderDto;
import com.example.project.dto.order.OrderRequest;
import com.example.project.dto.order.OrderResponse;
import com.example.project.dto.order.OrderSearchCondition;
import com.example.project.repository.order.SalesSearchCondition;
import com.example.project.service.OrderService;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final SseEmitters sseEmitters;

    @PostMapping("/api/orders")
    public ResponseEntity<?> addOrder(@RequestBody OrderRequest request) {

        try {
            String email = SecurityUtil.getCurrentUsername();
            Order savedOrder = orderService.addOrder(request, email);

            //마지막으로 추가된 주문의 ID 갱신
            log.info("saveOrder 데이터 존재 여부 orderSize = {}, order_id = {}", savedOrder.getOrderItems().size(), savedOrder.getId());

            // SSE를 통해 새로운 주문을 클라이언트에게 알리기
            sseEmitters.newOrder(savedOrder);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //음식점 id과 condition에 따라 [ORDER, READY, CANCEL, COMP] 출력
    //store_id, orderStatus, user 정보를 통해 목록을 가져와야 함 전용 DTO와 querydsl 코드 만들기
    @GetMapping("/api/orders/{id}")
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable("id") long store_id) {
        List<Order> orders = orderService.findAllByStore(store_id);

        List<OrderResponse> orderResponse = orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(orderResponse);
    }

    //사용자의 주문 내역
    @GetMapping("/api/orders")
    public ResponseEntity<Page<OrderDto>> getOrders(OrderSearchCondition condition, Pageable pageable) {

        Page<OrderDto> orders = orderService.searchOrder(condition, pageable);

        return ResponseEntity.ok()
                .body(orders);
    }

    //매출액 확인하기
    @GetMapping("/api/sales/{storeId}")
    public ResponseEntity<SalesDto> getSales(@PathVariable("storeId") long id, SalesSearchCondition condition) {

        SalesDto sales = orderService.searchSales(id, condition);

        return ResponseEntity.ok()
                .body(sales);
    }

    @GetMapping("/api/sales")
    public ResponseEntity<SalesDto> getSales(SalesSearchCondition condition) {

        SalesDto sales = orderService.searchAllSales(condition);

        return ResponseEntity.ok()
                .body(sales);
    }

    //접수대기[수락or거절], 처리중[배달or취소] 상태에서 둘중 하나를 눌렀을 때 orderStatus 변경 [READY, CANCEL, COMP]
    @PatchMapping("/api/orders/{id}/{status}")
    public ResponseEntity<Void> updateStatus(@PathVariable long id, @PathVariable String status) {

        Order order = orderService.updateStatus(id, status);

        //여기서 status 상태 값에 따라 다른 처리를 하면 된다?
        if (status.equals("READY")) {
            sseEmitters.processingOrder(order);
        }
        else if (status.equals("CANCEL")) {
            sseEmitters.cancelOrder(order);
        }
        else if (status.equals("COMP")) {
            sseEmitters.compOrder(order);
        }

        return ResponseEntity.ok()
                .build();
    }

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
        SseEmitter emitter = new SseEmitter(60 * 1000L);
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
