package com.example.project.repository.order;

import com.example.project.dto.order.OrderDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //[ORDER, READY, CANCEL, COMP] 상태를 구분해서 각각 넣어야 함
//    public Page<OrderDto>
}
