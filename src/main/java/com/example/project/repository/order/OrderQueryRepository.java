package com.example.project.repository.order;

import com.example.project.domain.QOrder;
import com.example.project.domain.QOrderItem;
import com.example.project.dto.order.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.project.domain.QOrder.*;
import static com.example.project.domain.QOrderItem.*;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //[ORDER, READY, CANCEL, COMP] 상태를 구분해서 각각 넣어야 함
//    public Page<OrderDto> search(long id, OrderSearchCondition condition, Pageable pageable) {
//        List<OrderDto> content = queryFactory
//                .select(new QOrderDto(
//                        new QOrderDataDto(
//                                order.id,
//                                order.user.name,
//                                order.user.address,
//                                order.status,
//                                order.createdDate,
//                                order.lastModifiedDate,
//                                new QOrderItemDto(
//                                        orderItem.item.name,
//                                        orderItem.orderPrice,
//                                        orderItem.count
//                                )
//                        ),
//                        new QOrderDataDto(
//                                order.id,
//                                order.user.name,
//                                order.user.address,
//                                order.status,
//                                order.createdDate,
//                                order.lastModifiedDate,
//                                new QOrderItemDto(
//                                        orderItem.item.name,
//                                        orderItem.orderPrice,
//                                        orderItem.count
//                                )
//                        ),
//                        new QOrderDataDto(
//                                order.id,
//                                order.user.name,
//                                order.user.address,
//                                order.status,
//                                order.createdDate,
//                                order.lastModifiedDate,
//                                new QOrderItem(
//                                        orderItem.item.name,
//                                        orderItem.orderPrice,
//                                        orderItem.count
//                                )
//                        ),
//                        new QOrderDataDto(
//                                order.id,
//                                order.user.name,
//                                order.user.address,
//                                order.status,
//                                order.createdDate,
//                                order.lastModifiedDate,
//                                new QOrderItem(
//                                        orderItem.item.name,
//                                        orderItem.orderPrice,
//                                        orderItem.count
//                                )
//                        )
//                ))
//                .from(order)
//                .leftJoin(order.orderItems, orderItem)
//                .where(
//
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//    }
}
