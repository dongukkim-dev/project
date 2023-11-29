package com.example.project.service;

import com.example.project.domain.Item;
import com.example.project.domain.Order;
import com.example.project.domain.OrderItem;
import com.example.project.domain.User;
import com.example.project.dto.order.OrderRequest;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    /**
     * 주문
     */
    public Order addOrder(List<OrderRequest> request, String email) {

        //엔티티 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("주문할 사람 정보가 없습니다"));


        //주문 상품 생성
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderRequest orderRequest : request) {
            Item item = itemService.findById(orderRequest.getItem_id());
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .orderPrice(item.getPrice())
                    .count(orderRequest.getAmount())
                    .build();

            //여기서 order 뭔가를 추가해주면 좋을거 같은데
            orderItems.add(orderItem);
        }

        //주문 생성
        Order order = Order.builder()
                .user(user)
                //store 정보가 들어가야 함 (어차피 다 같은 가게 상품이니 첫번째 상품 넣기
                .store(orderItems.get(0).getItem().getStore())
                .orderItems(orderItems)
                .build();

        Iterator<OrderItem> iterator = orderItems.iterator();
        while (iterator.hasNext()) {
            OrderItem orderItem = iterator.next();
            orderItem.setOrder(order);
        }

        //주문 저장
        orderRepository.save(order);
        return order;
    }
}
