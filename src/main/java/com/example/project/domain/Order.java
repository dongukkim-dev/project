package com.example.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //주문 상태, 수령자는 그냥 주문한 사람으로 다 통일하자 -> 주소
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    //== 빌더 생성자 ==//
    @Builder
    public Order(User user, Store store, OrderStatus status, List<OrderItem> orderItems) {
        //== 연관관계 편의 메서드 ==//
        this.user = user;
        user.getOrders().add(this);

        this.store = store;
        this.status = status;

        this.orderItems = orderItems;
//        OrderItem.builder().order(this).build();
    }
}
