package com.example.project.repository.item;

import com.example.project.domain.Item;
import com.example.project.domain.ItemStatus;
import com.example.project.dto.cart.CartResponse;
import com.example.project.dto.cart.QCartResponse;
import com.example.project.dto.item.ItemSearchCondition;
import com.example.project.dto.item.ItemStoreDto;
import com.example.project.dto.item.QItemStoreDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.project.domain.QItem.item;
import static com.example.project.domain.QStore.store;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ItemQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public CartResponse searchCartData(long id, int amount) {
        return queryFactory
                .select(new QCartResponse(
                        item.id,
                        item.name,
                        item.price,
                        Expressions.as(Expressions.constant(amount), "amount")
                ))
                .from(item)
                .where(item.id.eq(id))
                .fetchOne();
    }

    public Page<ItemStoreDto> search(long id, ItemSearchCondition condition, Pageable pageable) {
        List<ItemStoreDto> content = queryFactory
                .select(new QItemStoreDto(
                        item.id,
                        item.name,
                        item.content,
                        item.price,
                        item.picture,
                        item.status,
                        item.deleted,
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
                        itemStatus(condition.getStatus()),
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
                        storeName(condition.getStoreName()),
                        itemStatus(condition.getStatus())
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

    private BooleanExpression itemStatus(String status) {
        return hasText(status) ? item.status.eq(ItemStatus.valueOf(status)) : null;
    }
}
