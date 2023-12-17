package com.example.project.repository.order;

import com.example.project.domain.Order;
import com.example.project.domain.OrderStatus;
import com.example.project.dto.order.OrderDto;
import com.example.project.dto.order.OrderSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.project.domain.QOrder.order;
import static com.example.project.domain.QOrderItem.orderItem;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //[ORDER, READY, CANCEL, COMP] 상태를 구분해서 각각 넣어야 함
    public Page<OrderDto> searchOrders(OrderSearchCondition condition, Pageable pageable) {
        List<OrderDto> content = queryFactory
                .select(Projections.fields(OrderDto.class,
                        order
                ))
                .from(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .where(
                        userIdEq(condition.getUserId()),
                        storeIdEq(condition.getStoreId()),
                        orderStatus(condition.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Order> countQuery = queryFactory
                .selectFrom(order)
                .where(
                        userIdEq(condition.getUserId()),
                        storeIdEq(condition.getStoreId()),
                        orderStatus(condition.getStatus())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery.fetch()::size);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId == null ? null : order.user.id.eq(userId);
    }

    private BooleanExpression storeIdEq(Long storeId) {
        return storeId == null ? null : order.store.id.eq(storeId);
    }

    private BooleanExpression orderStatus(String status) {
        return hasText(status) ? order.status.eq(OrderStatus.valueOf(status)) : null;
    }
}
