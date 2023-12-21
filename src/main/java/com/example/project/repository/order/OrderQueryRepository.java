package com.example.project.repository.order;

import com.example.project.domain.Order;
import com.example.project.domain.OrderStatus;
import com.example.project.domain.QReview;
import com.example.project.domain.QUser;
import com.example.project.dto.SaleDto;
import com.example.project.dto.order.OrderDto;
import com.example.project.dto.order.OrderItemDto;
import com.example.project.dto.order.OrderSearchCondition;
import com.example.project.dto.review.ReviewResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.project.domain.QItem.item;
import static com.example.project.domain.QOrder.order;
import static com.example.project.domain.QOrderItem.orderItem;
import static com.example.project.domain.QReview.*;
import static com.example.project.domain.QStore.store;
import static com.example.project.domain.QUser.*;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    //[ORDER, READY, CANCEL, COMP] 상태를 구분해서 각각 넣어야 함
    public Page<OrderDto> searchOrders(OrderSearchCondition condition, Pageable pageable) {
        List<OrderDto> content = queryFactory
                .selectFrom(order)
                .leftJoin(order.orderItems, orderItem)
                .leftJoin(order.store, store)
                .leftJoin(order.user, user)
                .leftJoin(order.review, review)
                .leftJoin(orderItem.item, item)
                .where(
                        userIdEq(condition.getUserId()),
                        storeIdEq(condition.getStoreId()),
                        orderStatus(condition.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .transform(groupBy(order.id).list(
                        Projections.fields(OrderDto.class,
                                order.id.as("orderId"),
                                order.status.as("orderStatus"),
                                order.createdDate.as("orderDate"),
                                list(Projections.fields(
                                        OrderItemDto.class,
                                        orderItem.id.as("itemId"),
                                        orderItem.item.name.as("itemName"),
                                        orderItem.orderPrice.as("orderPrice"),
                                        orderItem.count.as("count")
                                )).as("orderItems"),
                                Projections.fields(
                                        ReviewResponse.class,
                                        order.review.id,
                                        order.store.name.as("storeName"),
                                        order.user.name.as("userName"),
                                        order.review.picture,
                                        order.review.content,
                                        order.review.rating,
                                        list(orderItem.item.name
                                        ).as("itemNames")
                                ).as("review"))
                ));

        JPAQuery<Order> countQuery = queryFactory
                .selectFrom(order)
                .leftJoin(order.orderItems, orderItem)
                .leftJoin(orderItem.item, item)
                .where(
                        userIdEq(condition.getUserId()),
                        storeIdEq(condition.getStoreId()),
                        orderStatus(condition.getStatus())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery.fetch()::size);
    }

    //매출 관리를 위한 데이터 (동적 쿼리 사용하기)
    public List<SaleDto> searchSales(SalesSearchCondition condition) {
        return queryFactory
                .select(Projections.fields(SaleDto.class,
                                Expressions.stringTemplate("DATE_FORMAT({0}, {1})", order.createdDate, "%Y%m%d").as("date"),
                                orderItem.orderPrice.multiply(orderItem.count).sum().as("sum")
                        ))
                .from(order)
                .leftJoin(order.orderItems, orderItem)
                .leftJoin(orderItem.item, item)
                .groupBy(Expressions.stringTemplate(
                        "DATE_FORMAT({0}, {1})", order.createdDate, "%Y%m%d"))
                .fetch();
    }

    //입력된 날짜 데이터가 아 이거 범위로 해야 한다
//    private BooleanExpression dateFilter(String dateCond) {
//        return hasText(dateCond) ? null : order.createdDate.eq(Expressions.stringTemplate("FUNCTION('FORMATDATE', {0}, '%Y-%m-%d')"));
//    }

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
