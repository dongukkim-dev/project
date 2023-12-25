package com.example.project.repository.store;

import com.example.project.domain.*;
import com.example.project.dto.BookmarkResponse;
import com.example.project.dto.BookmarkStatusResponse;
import com.example.project.dto.QBookmarkResponse;
import com.example.project.dto.QBookmarkStatusResponse;
import com.example.project.dto.store.StoreSearchCondition;
import com.example.project.dto.store.QStoreUserDto;
import com.example.project.dto.store.StoreSimpleInfoResponse;
import com.example.project.dto.store.StoreUserDto;
import com.example.project.repository.BookmarkRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.project.domain.QBookmark.*;
import static com.example.project.domain.QCategory.*;
import static com.example.project.domain.QReview.*;
import static com.example.project.domain.QStore.store;
import static com.example.project.domain.QUser.user;
import static org.springframework.util.StringUtils.hasText;

/**
 * api에 특화되어 있는 종속적인 코드들을 따로 빼서 만들기
 */
@Repository
public class StoreQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final BookmarkRepository bookmarkRepository;

    public StoreQueryRepository(EntityManager em, BookmarkRepository bookmarkRepository) {
        this.queryFactory = new JPAQueryFactory(em);
        this.bookmarkRepository = bookmarkRepository;
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

    //메인 화면의 데이터 review 값을 가져와야 함
    public List<StoreSimpleInfoResponse> searchMainInfo() {
        return queryFactory
                .select(Projections.fields(StoreSimpleInfoResponse.class,
                        store.id,
                        store.name.as("name"),
                        store.picture,
                        store.category.name.as("category"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(review.rating.avg())
                                        .from(review)
                                        .where(review.store.id.eq(store.id)), "rating"
                        )
                ))
                .from(store)
                .leftJoin(store.reviews, review)
                .leftJoin(store.category, category)
                .groupBy(store.id)
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

    //해당 가게의 bookmark 여부와 총 인원 수 반환
    public BookmarkStatusResponse bookmarkStatus(User user, Store storeInfo) {
        BookmarkStatusResponse content = queryFactory
                .select(new QBookmarkStatusResponse(
                        Expressions.asBoolean(false),
                        store.bookmarks.size()
                ))
                .from(store)
                .where(
                        store.id.eq(storeInfo.getId())
                )
                .fetchFirst();

        if (user != null) {
            if (bookmarkRepository.existsBookmarkByUserAndStore(user, storeInfo)) {
                content.setStatus(true);
            }
        }

        return content;
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
