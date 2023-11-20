package com.example.project;

import com.example.project.domain.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 테스트 데이터를 생성
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    public void init() {

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        /**
         * yser1이 생성된 store1에서 item을 order하는 테스트
         */
        public void dbInit1() {
            User user = User.builder()
                    .email("test@asd.123")
                    .password("1234")
                    .nickname("kim")
                    .grade(Grade.BRONZE)
                    .point(0)
                    .gender(Gender.MAN)
                    .build();
            em.persist(user);

            Store store = Store.builder()
                    .name("일성만두")
                    .picture("대표 사진 url")
                    .content("상세 내용1")
                    .rating(4.3)
                    .build();
            em.persist(store);

            Item item1 = Item.builder()
                    .name("김치만두")
                    .price(2000)
                    .picture("사진1 url")
                    .content("김치가 들어간 만두입니다.")
                    .stockQuantity(1000)
                    .store(store)
                    .build();
            em.persist(item1);

            Item item2 = Item.builder()
                    .name("고기만두")
                    .price(1500)
                    .picture("사진2 url")
                    .content("고기가 들어간 만두입니다.")
                    .stockQuantity(1000)
                    .store(store)
                    .build();
            em.persist(item2);
        }

        public void dbInit2() {
            User user = User.builder()
                    .email("test@qwe.456")
                    .password("4567")
                    .nickname("park")
                    .grade(Grade.BRONZE)
                    .point(0)
                    .gender(Gender.MAN)
                    .build();
            em.persist(user);

            Store store = Store.builder()
                    .name("피자나라")
                    .picture("대표 사진 url")
                    .content("상세 내용1")
                    .rating(4.7)
                    .build();
            em.persist(store);

            Item item1 = Item.builder()
                    .name("파인애플피자")
                    .price(12000)
                    .picture("사진1 url")
                    .content("파인애플이 들어간 피자입니다.")
                    .stockQuantity(300)
                    .store(store)
                    .build();
            em.persist(item1);

            Item item2 = Item.builder()
                    .name("치즈피자")
                    .price(10000)
                    .picture("사진2 url")
                    .content("치즈가 들어간 피자입니다.")
                    .stockQuantity(400)
                    .store(store)
                    .build();
            em.persist(item2);

            Item item3 = Item.builder()
                    .name("포테이토피자")
                    .price(13000)
                    .picture("사진3 url")
                    .content("감자가 들어간 피자입니다.")
                    .stockQuantity(400)
                    .store(store)
                    .build();
            em.persist(item3);
        }
    }
}
