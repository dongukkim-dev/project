package com.example.project.repository.store;

import com.example.project.domain.QBookmark;
import com.example.project.dto.BookmarkResponse;
import com.example.project.dto.QBookmarkResponse;
import com.example.project.dto.store.StoreSearchCondition;
import com.example.project.dto.store.QStoreUserDto;
import com.example.project.dto.store.StoreUserDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.project.domain.QBookmark.*;
import static com.example.project.domain.QStore.store;
import static com.example.project.domain.QUser.user;
import static org.springframework.util.StringUtils.hasText;

/**
 * api에 특화되어 있는 종속적인 코드들을 따로 빼서 만들기
 */
@Repository
public class StoreQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StoreQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
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

    //bookmark 불러오기
    public List<BookmarkResponse> searchBookmark(String email) {
        return queryFactory
                .select(new QBookmarkResponse(
                        bookmark.id,
                        store.id,
                        store.name
                ))
                .from(bookmark)
                .leftJoin(bookmark.store, store)
                .leftJoin(bookmark.user, user)
                .where(
                        user.email.eq(email)
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
