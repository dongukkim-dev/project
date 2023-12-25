package com.example.project.repository.store;

import com.example.project.domain.Store;
import com.example.project.dto.store.StoreSearchCondition;
import com.example.project.dto.store.QStoreUserDto;
import com.example.project.dto.store.StoreUserDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.example.project.domain.QStore.store;
import static com.example.project.domain.QUser.user;
import static org.springframework.util.StringUtils.hasText;

public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StoreRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<StoreUserDto> search(StoreSearchCondition condition) {
        return queryFactory
                .select(new QStoreUserDto(
                        store.id,
                        store.name,
                        store.content,
                        user.id,
                        user.name
                ))
                .from(store)
                .leftJoin(store.user, user)
                .where(
                        storeNameEq(condition.getStoreName()),
                        userNameEq(condition.getUserName())
                )
                .fetch();
    }

    private BooleanExpression storeNameEq(String storeName) {
        return hasText(storeName) ? store.name.eq(storeName) : null;
    }

    private BooleanExpression userNameEq(String userName) {
        return hasText(userName) ? user.name.eq(userName) : null;
    }
}
