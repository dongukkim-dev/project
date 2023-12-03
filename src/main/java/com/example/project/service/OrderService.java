package com.example.project.service;

import com.example.project.domain.*;
import com.example.project.dto.order.OrderRequest;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.UserRepository;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    //SSE 기본 타임아웃 설정
    private static final long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    /**
     * 주문 - OrderRequest에 있는 정보 (item_id, amount)를 받아서 생성
     * 로그인된 유저 정보를 통해 수령자, 전화번호, 주소를 가져온다
     */
    public Order addOrder(List<OrderRequest> request, String email) {

        //엔티티 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("주문할 사람 정보가 없습니다"));


        //주문 상품 생성
        OrderItem[] orderItems = new OrderItem[request.size()];
        for (int i=0; i< request.size(); ++i) {
            Item item = itemService.findById(request.get(i).getItem_id());
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .orderPrice(item.getPrice())
                    .count(request.get(i).getAmount())
                    .build();

            orderItems[i] = orderItem;
        }

        //주문 생성
        Order order = Order.createOrder(user, orderItems[0].getItem().getStore(), orderItems);

        //주문 저장
        orderRepository.save(order);
        return order;
    }

    //해당 음식점에 들어온 주문들 반환
    public List<Order> findAllByStore(Long store_id) {
        return orderRepository.findAllByStore(store_id);
    }

    //update

    //delete
    @Transactional
    public void delete(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeOrderAuthor(order);
        order.deletedChange();
    }

    //주문을 한 유저인지 확인
    private static void authorizeOrderAuthor(Order order) {
        String email = SecurityUtil.getCurrentUsername();
        if (!order.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

    /**
     * SSE 통신을 위해 클라이언트가 구독을 위해 호출하는 메서드
     */
//    public SseEmitter subscribe(Long userId) {
//        SseEmitter emitter;
//
//        sendTo
//    }
//
//    //클라이언트에게 데이터를 전송
//
//    //사용자 아이디를 기반으로 이벤트 Emitter를 생성
//    private SseEmitter createEmitter(Long id) {
//        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
//    }
}
