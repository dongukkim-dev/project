package com.example.project.controller;

import com.example.project.domain.Order;
import com.example.project.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
@RequiredArgsConstructor
public class SseEmitters {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    SseEmitter add(SseEmitter emitter) {
        this.emitters.add(emitter);
        log.info("new emitter added: {}", emitter);
        log.info("emitter list size : {}", emitters.size());
        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitters.remove(emitter);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
        });

        return emitter;
    }

    public void newOrder(Order order) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("newOrder")
                        .data(new OrderResponse(order)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void processingOrder(Order order) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("processingOrder")
                        .data(new OrderResponse(order)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //여기서 event 발생시 주문 취소, 배달 완료로 이동시키는 코드를 추가해야 함
    public void cancelOrder(Order order) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("cancelOrder")
                        .data(new OrderResponse(order)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void compOrder(Order order) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("compOrder")
                        .data(new OrderResponse(order)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
