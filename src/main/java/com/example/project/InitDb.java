package com.example.project;

import com.example.project.domain.*;
import com.example.project.dto.signup.SignUpRequest;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 테스트 데이터를 생성
 */
@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
        initService.dbInit4();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        @PersistenceContext
        private final EntityManager em;

        //비밀번호 암호화용
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        /**
         * 일반 회원, 가맹점 회원
         */
        public void dbInit1() {
            User admin = User.builder()
                    .email("test@admin.123")
                    .password(encoder.encode("asdf1234!"))
                    .name("lee")
                    .phone("010-8888-3333")
                    .address("주소4")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_ADMIN)
                    .build();
            em.persist(admin);

            User user1 = User.builder()
                    .email("test@test.123")
                    .password(encoder.encode("asdf1234!"))
                    .name("lee")
                    .phone("010-8888-3333")
                    .address("주소4")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_USER)
                    .build();
            em.persist(user1);

            User user = User.builder()
                    .email("test@asd.123")
                    .password(encoder.encode("asdf1234!"))
                    .name("kim")
                    .phone("010-1111-2222")
                    .address("주소1")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_STORE)
                    .build();
            em.persist(user);

            Store store = Store.builder()
                    .user(user)
                    .name("일성만두")
                    .address("경기도 하남시")
                    .phone("070-1111-2222")
                    .picture("대표 사진 url")
                    .content("상세 내용1")
                    .rating(4.3)
                    .openTime("10:00")
                    .closeTime("21:00")
                    .build();
            em.persist(store);

            Item item1 = Item.builder()
                    .name("김치만두")
                    .price(2000)
                    .picture("사진1 url")
                    .content("김치가 들어간 만두입니다.")
                    .store(store)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item1);

            Item item2 = Item.builder()
                    .name("고기만두")
                    .price(1500)
                    .picture("사진2 url")
                    .content("고기가 들어간 만두입니다.")
                    .store(store)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item2);
        }

        public void dbInit2() {
            User user = User.builder()
                    .email("test@qwe.456")
                    .password(encoder.encode("asdf1234!"))
                    .name("park")
                    .phone("010-3333-2222")
                    .address("주소2")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_STORE)
                    .build();
            em.persist(user);

            Store store = Store.builder()
                    .user(user)
                    .name("피자나라")
                    .address("서울시")
                    .phone("070-2222-1111")
                    .picture("대표 사진 url")
                    .content("상세 내용1")
                    .rating(4.7)
                    .openTime("10:00")
                    .closeTime("22:00")
                    .build();
            em.persist(store);

            Item item1 = Item.builder()
                    .name("파인애플피자")
                    .price(12000)
                    .picture("사진1 url")
                    .content("파인애플이 들어간 피자입니다.")
                    .store(store)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item1);

            Item item2 = Item.builder()
                    .name("치즈피자")
                    .price(10000)
                    .picture("사진2 url")
                    .content("치즈가 들어간 피자입니다.")
                    .store(store)
                    .status(ItemStatus.SOLD)
                    .build();
            em.persist(item2);

            Item item3 = Item.builder()
                    .name("포테이토피자")
                    .price(13000)
                    .picture("사진3 url")
                    .content("감자가 들어간 피자입니다.")
                    .store(store)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item3);
        }

        public void dbInit3() {
            User user1 = User.builder()
                    .email("test2@asd.123")
                    .password(encoder.encode("asdf1234!"))
                    .name("일반회원2")
                    .phone("010-5555-1111")
                    .address("일반 회원 주소2")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_USER)
                    .build();
            em.persist(user1);

            User user2 = User.builder()
                    .email("test@zxc.456")
                    .password(encoder.encode("asdf1234!"))
                    .name("choi")
                    .phone("010-5555-1111")
                    .address("주소3")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_STORE)
                    .build();
            em.persist(user2);

            Store store = Store.builder()
                    .user(user2)
                    .name("과자공장")
                    .address("경기도 남양주시")
                    .phone("031-571-1111")
                    .picture("대표 사진 url3")
                    .content("상세 내용3")
                    .rating(3.9)
                    .openTime("12:00")
                    .closeTime("19:00")
                    .build();
            em.persist(store);

            Item item1 = Item.builder()
                    .name("파인애플피자")
                    .price(12000)
                    .picture("사진1 url")
                    .content("파인애플이 들어간 피자입니다.")
                    .store(store)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item1);

            Item item2 = Item.builder()
                    .name("파인애플피자")
                    .price(10000)
                    .picture("사진2 url")
                    .content("파인애플이 들어간 피자")
                    .store(store)
                    .status(ItemStatus.SOLD)
                    .build();
            em.persist(item2);

            Item item3 = Item.builder()
                    .name("파인애플조각피자")
                    .price(12000)
                    .picture("사진3 url")
                    .content("파인애플이 들어간 조각피자입니다.")
                    .store(store)
                    .status(ItemStatus.SOLD)
                    .build();
            em.persist(item3);

           for (int i=0; i<100; i++) {
               em.persist(Item.builder()
                               .store(store)
                               .name("item" + i)
                               .price(1000 + i)
                               .picture("대표 사진" + i)
                               .content("상세 내용" + i)
                               .status(ItemStatus.SALE)
                       .build());
           }

           //주문 코드 생각하기
//            Order order = Order.createOrder(user1, store, item1, item2, item3);

        }

        //주문 테스트 추가
        public void dbInit4() {
            User user = User.builder()
                    .email("test3@asd.123")
                    .password(encoder.encode("asdf1234!"))
                    .name("일반회원3")
                    .phone("010-5555-1111")
                    .address("일반 회원 주소3")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_USER)
                    .build();
            em.persist(user);
        }
    }
}
