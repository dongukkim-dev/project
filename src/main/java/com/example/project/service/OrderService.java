package com.example.project.service;

import com.example.project.domain.*;
import com.example.project.dto.order.OrderDto;
import com.example.project.dto.order.OrderRequest;
import com.example.project.dto.order.OrderResponse;
import com.example.project.dto.order.OrderSearchCondition;
import com.example.project.repository.order.OrderQueryRepository;
import com.example.project.repository.order.OrderRepository;
import com.example.project.repository.user.UserRepository;
import com.example.project.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final OrderQueryRepository orderQueryRepository;

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
        log.info("request size = {}", request.size());
        log.info("orderItems size = {}", orderItems.length);
        for (int i=0; i< request.size(); ++i) {
            Item item = itemService.findById(request.get(i).getItem_id());
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .orderPrice(item.getPrice())
                    .count(request.get(i).getAmount())
                    .build();

            orderItems[i] = orderItem;
        }

        log.info("최종 orderItems 사이즈: {}", Arrays.stream(orderItems).count());

        //주문 생성
        Order order = Order.createOrder(user, orderItems[0].getItem().getStore(), orderItems);

        log.info("order에서의 orderItems size = {}", order.getOrderItems().size());

        //주문 저장
        orderRepository.save(order);
        return order;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다. id=" + id));
    }

    //해당 음식점에 들어온 주문들 반환
    public List<Order> findAllByStore(Long store_id) {
        return orderRepository.findAllByStore(store_id);
    }

    //관리자 페이지에서 필요한지 회원 페이지에서 필요한지 조건 검색
    public Page<OrderDto> searchOrder(OrderSearchCondition condition, Pageable pageable) {
        return orderQueryRepository.searchOrders(condition, pageable);
    }

    //마지막으로 추가된 주문만 반환
    public Order findLastOrderByStore(Long store_id, Long order_id) {
        return orderRepository.findLastOrderByStore(store_id, order_id);
    }

    //update
    @Transactional
    public Order updateStatus(long id, String status) {
        Order order = findById(id);
        order.updateStatus(status);

        return order;
    }

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
}
