package com.example.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE orders SET deleted = true WHERE order_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    //배송 완료 시간 저장 예정

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //주문 상태, 수령자는 그냥 주문한 사람으로 다 통일하자 -> 주소
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    @ColumnDefault("false")
    private boolean deleted = Boolean.FALSE;

    //== 빌더 생성자 ==//
    @Builder
    public Order(User user, Store store, OrderStatus status, List<OrderItem> orderItems) {
        this.user = user;
        this.store = store;
        this.status = status;
        this.orderItems = orderItems;
    }

    //==생성 메소드==//
    public static Order createOrder(User user, Store store, OrderItem... orderItems) {
        final Order order = Order.builder()
                .user(user)
                .store(store)
                .status(OrderStatus.ORDER)
                .orderItems(new ArrayList<>())
                .build();

        Arrays.stream(orderItems).forEach(order::addOrderItem);

        return order;
    }

    //== 연관관계 메서드 ==//
    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
    }

    //== 주문을 지우는 deleted flag ==//
    public void deletedChange() {
        this.deleted = true;
    }
}
