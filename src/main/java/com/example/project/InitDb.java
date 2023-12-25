package com.example.project;

import com.example.project.domain.*;
import com.example.project.dto.item.AddItemRequest;
import com.example.project.dto.order.OrderRequest;
import com.example.project.service.ItemService;
import com.example.project.service.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
//        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        @PersistenceContext
        private final EntityManager em;

        //비밀번호 암호화용
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        private final OrderService orderService;
        private final ItemService itemService;

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

            User user2 = User.builder()
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
            em.persist(user2);

            User user3 = User.builder()
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
            em.persist(user3);

            User store_user1 = User.builder()
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
            em.persist(store_user1);

            User store_user2 = User.builder()
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
            em.persist(store_user2);

            User store_user3 = User.builder()
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
            em.persist(store_user3);

            //유저 정보만 있고 store 정보는 없는 계정
            User store_user4 = User.builder()
                    .email("store1@asd.123")
                    .password(encoder.encode("asdf1234!"))
                    .name("가맹점4")
                    .phone("010-5555-1111")
                    .address("가맹점 유저 주소4")
                    .grade(Grade.BRONZE)
                    .gender(Gender.MALE)
                    .point(0)
                    .role(Role.ROLE_STORE)
                    .build();
            em.persist(store_user4);

            Store store1 = Store.builder()
                    .user(store_user1)
                    .name("일성만두")
                    .address("경기도 하남시")
                    .phone("070-1111-2222")
                    .picture("upload\\storeImg\\231215\\만두3.jpg")
                    .content("상세 내용1")
                    .openTime("10:00")
                    .closeTime("21:00")
                    .minOrderPrice(6000)
                    .build();
            em.persist(store1);

            Store store2 = Store.builder()
                    .user(store_user2)
                    .name("피자나라")
                    .address("서울시")
                    .phone("070-2222-1111")
                    .picture("upload\\storeImg\\231215\\피자1.jpg")
                    .content("상세 내용1")
                    .openTime("10:00")
                    .closeTime("22:00")
                    .minOrderPrice(20000)
                    .build();
            em.persist(store2);

            Store store3 = Store.builder()
                    .user(store_user3)
                    .name("치킨공장")
                    .address("경기도 남양주시")
                    .phone("031-571-1111")
                    .picture("upload\\storeImg\\231215\\치킨1.jpg")
                    .content("상세 내용3")
                    .openTime("12:00")
                    .closeTime("19:00")
                    .minOrderPrice(16000)
                    .build();
            em.persist(store3);

            Item item1 = Item.builder()
                    .name("김치만두")
                    .price(2000)
                    .picture("upload\\itemImg\\231215\\김치만두.jpg")
                    .content("김치가 들어간 만두입니다.")
                    .store(store1)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item1);

            Item item2 = Item.builder()
                    .name("고기만두")
                    .price(1500)
                    .picture("upload\\itemImg\\231215\\고기만두.jpg")
                    .content("고기가 들어간 만두입니다.")
                    .store(store1)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item2);

            Item item3 = Item.builder()
                    .name("파인애플피자")
                    .price(12000)
                    .picture("upload\\itemImg\\231215\\파인애플피자.jpg")
                    .content("파인애플이 들어간 피자입니다.")
                    .store(store2)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item3);

            Item item4 = Item.builder()
                    .name("치즈피자")
                    .price(10000)
                    .picture("upload\\itemImg\\231215\\치즈피자.jpg")
                    .content("치즈가 들어간 피자입니다.")
                    .store(store2)
                    .status(ItemStatus.SOLD)
                    .build();
            em.persist(item4);

            Item item5 = Item.builder()
                    .name("포테이토피자")
                    .price(13000)
                    .picture("upload\\itemImg\\231215\\포테이토피자.jpg")
                    .content("감자가 들어간 피자입니다.")
                    .store(store2)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item5);

            Item item6 = Item.builder()
                    .name("도이치피자")
                    .price(13000)
                    .picture("upload\\itemImg\\231215\\도이치피자.jpg")
                    .content("도이치 피자입니다.")
                    .store(store2)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item6);

            Item item7 = Item.builder()
                    .name("후라이드치킨")
                    .price(12000)
                    .picture("upload\\itemImg\\231215\\후라이드치킨.jpg")
                    .content("일반 후라이드 치킨입니다")
                    .store(store3)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item7);

            Item item8 = Item.builder()
                    .name("양념치킨")
                    .price(10000)
                    .picture("upload\\itemImg\\231215\\양념치킨.jpg")
                    .content("양념 치킨입니다")
                    .store(store3)
                    .status(ItemStatus.SOLD)
                    .build();
            em.persist(item8);

            Item item9 = Item.builder()
                    .name("뿌링클치킨")
                    .price(12000)
                    .picture("upload\\itemImg\\231215\\뿌링클치킨.jpg")
                    .content("뿌링클 치킨입니다")
                    .store(store3)
                    .status(ItemStatus.SOLD)
                    .build();
            em.persist(item9);

            Item item10 = Item.builder()
                    .name("간장치킨")
                    .price(11000)
                    .picture("upload\\itemImg\\231215\\간장치킨.jpg")
                    .content("간장이 들어간 치킨입니다")
                    .store(store3)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item10);

            Item item11 = Item.builder()
                    .name("마늘치킨")
                    .price(14000)
                    .picture("upload\\itemImg\\231215\\마늘치킨.jpg")
                    .content("마늘이 들어간 치킨입니다")
                    .store(store3)
                    .status(ItemStatus.SALE)
                    .build();
            em.persist(item11);


            //단순한 페이징용 더미 데이터 들
            for (int i=0; i<50; i++) {
                AddItemRequest itemRequest = new AddItemRequest("치킨" + i, 16000 + i, "upload\\itemImg\\231215\\테스트용.jpg", "상세정보" + i + " 입니다.");
                itemService.save(3, null, itemRequest);
            }

            try {
                // 파일을 읽어와서 byte 배열로 변환
                byte[] fileData = Files.readAllBytes(Paths.get("C:\\delivery\\upload\\itemImg\\231215\\NoImage.avif"));

                // 파일의 확장자를 추출
                String originalFileName = Paths.get("C:\\delivery\\upload\\itemImg\\231215\\NoImage.avif").getFileName().toString();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

                // MockMultipartFile로 변환
//                MultipartFile file = new MockMultipartFile("file", originalFileName, "image/" + fileExtension, fileData);

                // 서비스 메서드 호출
                AddItemRequest itemRequest1 = new AddItemRequest("직화구이피자", 16000, "upload\\itemImg\\231215\\직화구이피자.jpg", "직화구이 피자입니다");
                itemService.save(2, null, itemRequest1);

            } catch (IOException e) {
                // 파일 읽기 예외 처리
                e.printStackTrace();
            }

            /**
             * user1 = test@test.123, , store1 = test@asd.123 store2 = test2@asd.123, store3 = test3@asd.123
             */
            //user1, store2의 주문 내역 추가하기
/*
            List<OrderRequest> requests1 = new ArrayList<>();
            OrderRequest request1 = new OrderRequest(item5.getId(), 2);
            OrderRequest request2 = new OrderRequest(item4.getId(), 1);
            requests1.add(request1);
            requests1.add(request2);

            orderService.addOrder(requests1, user1.getEmail());

            //user1, store3의 주문 내역 추가
            List<OrderRequest> requests2 = new ArrayList<>();
            OrderRequest request3 = new OrderRequest(item7.getId(), 2);
            OrderRequest request4 = new OrderRequest(item8.getId(), 1);
            OrderRequest request5 = new OrderRequest(item10.getId(), 4);
            requests2.add(request3);
            requests2.add(request4);
            requests2.add(request5);

            orderService.addOrder(requests2, user1.getEmail());

            */
/**
             * user2 = test2@asd.123
             *//*

            //user2, store1의 주문 내역 추가
            List<OrderRequest> requests3 = new ArrayList<>();
            OrderRequest request6 = new OrderRequest(item1.getId(), 5);
            OrderRequest request7 = new OrderRequest(item2.getId(), 4);
            requests3.add(request6);
            requests3.add(request7);

            orderService.addOrder(requests3, user2.getEmail());

            //user2, store3의 주문 내역 추가
            List<OrderRequest> requests4 = new ArrayList<>();
            OrderRequest request8 = new OrderRequest(item9.getId(), 2);
            OrderRequest request9 = new OrderRequest(item10.getId(), 1);
            OrderRequest request10 = new OrderRequest(item11.getId(), 3);
            requests4.add(request8);
            requests4.add(request9);
            requests4.add(request10);

            orderService.addOrder(requests4, user1.getEmail());

            */
/**
             * test3@asd.123
             *//*

            //user3, store1의 주문 내역 추가
            List<OrderRequest> requests5 = new ArrayList<>();
            OrderRequest request11 = new OrderRequest(item1.getId(), 9);
            OrderRequest request12 = new OrderRequest(item2.getId(), 3);
            requests5.add(request11);
            requests5.add(request12);

            orderService.addOrder(requests5, user3.getEmail());

            //user3, store1의 주문 내역 추가
            List<OrderRequest> requests6 = new ArrayList<>();
            OrderRequest request13 = new OrderRequest(item1.getId(), 20);
            OrderRequest request14 = new OrderRequest(item2.getId(), 18);
            requests6.add(request13);
            requests6.add(request14);

            orderService.addOrder(requests6, user3.getEmail());

            //user3, store1의 주문 내역 추가
            List<OrderRequest> requests7 = new ArrayList<>();
            OrderRequest request15 = new OrderRequest(item1.getId(), 3);
            requests7.add(request15);

            orderService.addOrder(requests7, user3.getEmail());

            //user3, store2의 주문 내역 추가
            List<OrderRequest> requests8 = new ArrayList<>();
            OrderRequest request16 = new OrderRequest(item3.getId(), 2);
            requests8.add(request16);

            orderService.addOrder(requests8, user3.getEmail());

            //user3, store3의 주문 내역 추가
            List<OrderRequest> requests9 = new ArrayList<>();
            OrderRequest request17 = new OrderRequest(item7.getId(), 4);
            OrderRequest request18 = new OrderRequest(item9.getId(), 5);
            OrderRequest request19 = new OrderRequest(item11.getId(), 1);
            requests9.add(request17);
            requests9.add(request18);
            requests9.add(request19);

            orderService.addOrder(requests9, user3.getEmail());

            //user3, store3의 주문 내역 추가
            List<OrderRequest> requests10 = new ArrayList<>();
            OrderRequest request20 = new OrderRequest(item8.getId(), 7);
            OrderRequest request21 = new OrderRequest(item9.getId(), 3);
            OrderRequest request22 = new OrderRequest(item10.getId(), 4);
            OrderRequest request23 = new OrderRequest(item11.getId(), 8);
            requests10.add(request20);
            requests10.add(request21);
            requests10.add(request22);
            requests10.add(request23);

            orderService.addOrder(requests10, user3.getEmail());

            //user3, store3의 주문 내역 추가
            List<OrderRequest> requests11 = new ArrayList<>();
            OrderRequest request24 = new OrderRequest(item7.getId(), 3);
            OrderRequest request25 = new OrderRequest(item8.getId(), 2);
            requests11.add(request24);
            requests11.add(request25);

            orderService.addOrder(requests11, user3.getEmail());
*/
        }
    }
}
