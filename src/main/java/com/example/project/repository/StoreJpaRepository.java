package com.example.project.repository;

import com.example.project.domain.Store;
import com.example.project.dto.store.StoreSearchCondition;
import com.example.project.dto.store.QStoreUserDto;
import com.example.project.dto.store.StoreUserDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.project.domain.QStore.*;
import static com.example.project.domain.QUser.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class StoreJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StoreJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Store store) {
        em.persist(store);
    }

    public Optional<Store> findById(Long id) {
        Store findStore = em.find(Store.class, id);
        return Optional.ofNullable(findStore);
    }

    public List<Store> findAll() {
        return em.createQuery("select s from Store s", Store.class)
                .getResultList();
    }

    public List<Store> findAll_Querydsl() {
        return queryFactory
                .selectFrom(store)
                .fetch();
    }

    public List<Store> findByName(String storeName) {
        return em.createQuery("select s from Store s where s.name = :name", Store.class)
                .setParameter("name", storeName)
                .getResultList();
    }

    public List<Store> findByName_Querydsl(String userName) {
        return queryFactory
                .selectFrom(store)
                .where(store.name.eq(userName))
                .fetch();
    }

    public List<StoreUserDto> searchByBuilder(StoreSearchCondition condition) {

        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getStoreName())) {
            builder.and(store.name.eq(condition.getStoreName()));
        }
        if (hasText(condition.getUserName())) {
            builder.and(user.name.eq(condition.getUserName()));
        }

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
                .where(builder)
                .fetch();
    }

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

    //뭐 이런 식으로 엮어서 쓸 수 있다? 대신 null 체크 조심해야 함
    private BooleanExpression NameBetween(String storeName, String userName) {
        return storeNameEq(storeName).and(userNameEq(userName));
    }

    private BooleanExpression storeNameEq(String storeName) {
        return hasText(storeName) ? store.name.eq(storeName) : null;
    }

    private BooleanExpression userNameEq(String userName) {
        return hasText(userName) ? user.name.eq(userName) : null;
    }
}
