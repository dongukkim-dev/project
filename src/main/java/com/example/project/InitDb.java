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
         * yser1이 생성된 store1에서 item을 order하는 테스트
         */
        public void dbInit1() {
            User user = User.builder()
                    .email("test@asd.123")
                    .password(encoder.encode("1234"))
                    .name("kim")
                    .phone("010-1111-2222")
                    .address("주소1")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_ADMIN)
                    .build();
            em.persist(user);

            Store store = Store.builder()
                    .user(user)
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
                    .store(store)
                    .build();
            em.persist(item1);

            Item item2 = Item.builder()
                    .name("고기만두")
                    .price(1500)
                    .picture("사진2 url")
                    .content("고기가 들어간 만두입니다.")
                    .store(store)
                    .build();
            em.persist(item2);
        }

        public void dbInit2() {
            User user = User.builder()
                    .email("test@qwe.456")
                    .password(encoder.encode("4567"))
                    .name("park")
                    .phone("010-3333-2222")
                    .address("주소2")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_ADMIN)
                    .build();
            em.persist(user);

            Store store = Store.builder()
                    .user(user)
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
                    .store(store)
                    .build();
            em.persist(item1);

            Item item2 = Item.builder()
                    .name("치즈피자")
                    .price(10000)
                    .picture("사진2 url")
                    .content("치즈가 들어간 피자입니다.")
                    .store(store)
                    .build();
            em.persist(item2);

            Item item3 = Item.builder()
                    .name("포테이토피자")
                    .price(13000)
                    .picture("사진3 url")
                    .content("감자가 들어간 피자입니다.")
                    .store(store)
                    .build();
            em.persist(item3);
        }

        public void dbInit3() {
            User user = User.builder()
                    .email("test@zxc.456")
                    .password(encoder.encode("1234"))
                    .name("choi")
                    .phone("010-5555-1111")
                    .address("주소3")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_ADMIN)
                    .build();
            em.persist(user);

            Store store = Store.builder()
                    .user(user)
                    .name("과자공장")
                    .picture("대표 사진 url3")
                    .content("상세 내용3")
                    .rating(3.9)
                    .build();
            em.persist(store);

            Item item1 = Item.builder()
                    .name("파인애플피자")
                    .price(12000)
                    .picture("사진1 url")
                    .content("파인애플이 들어간 피자입니다.")
                    .store(store)
                    .build();
            em.persist(item1);

           for (int i=0; i<100; i++) {
               em.persist(Item.builder()
                               .store(store)
                               .name("item" + i)
                               .price(1000 + i)
                               .picture("대표 사진" + i)
                               .content("상세 내용" + i)
                       .build());
           }
        }
    }
}
