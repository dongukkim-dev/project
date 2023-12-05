package com.example.project.repository.item;

import com.example.project.domain.Item;
import com.example.project.domain.ItemStatus;
import com.example.project.domain.QItem;
import com.example.project.domain.QStore;
import com.example.project.dto.item.ItemSearchCondition;
import com.example.project.dto.item.ItemStoreDto;
import com.example.project.dto.item.QItemStoreDto;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.project.domain.QItem.*;
import static com.example.project.domain.QStore.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class ItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ItemQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<ItemStoreDto> search(long id, ItemSearchCondition condition, Pageable pageable) {
        List<ItemStoreDto> content = queryFactory
                .select(new QItemStoreDto(
                        item.id,
                        item.name,
                        item.price,
                        item.status,
                        store.id,
                        store.name
                ))
                .from(item)
                .leftJoin(item.store, store)
                .where(
                        itemNameEq(condition.getItemName()),
                        priceGoe(condition.getPriceGoe()),
                        priceLoe(condition.getPriceLoe()),
                        storeName(condition.getStoreName()),
//                        itemStatus(condition.getStatus()),
                        store.id.eq(id)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Item> countQuery = queryFactory
                .selectFrom(item)
                .leftJoin(item.store, store)
                .where(
                        itemNameEq(condition.getItemName()),
                        priceGoe(condition.getPriceGoe()),
                        priceLoe(condition.getPriceLoe()),
                        storeName(condition.getStoreName())
//                        itemStatus(condition.getStatus())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery.fetch()::size);
    }

    private BooleanExpression itemNameEq(String itemName) {
        return hasText(itemName) ? item.name.eq(itemName) : null;
    }

    private BooleanExpression priceGoe(Integer priceGoe) {
        return priceGoe == null ? null : item.price.goe(priceGoe);
    }

    private BooleanExpression priceLoe(Integer priceLoe) {
        return priceLoe == null ? null : item.price.loe(priceLoe);
    }

    private BooleanExpression storeName(String storeName) {
        return hasText(storeName) ? store.name.eq(storeName) : null;
    }

    private BooleanExpression itemStatus(ItemStatus status) {
        return hasText(String.valueOf(status)) ? item.status.eq(status) : null;
    }
}