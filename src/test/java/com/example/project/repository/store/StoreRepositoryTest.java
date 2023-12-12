package com.example.project.repository.store;

import com.example.project.domain.*;
import com.example.project.dto.store.StoreSearchCondition;
import com.example.project.dto.store.StoreUserDto;
import com.example.project.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StoreRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void basicTest() {
        User user = User.builder()
                .email("test1")
                .password("1234")
                .name("kim")
                .phone("010-1111-2222")
                .address("주소1")
                .grade(Grade.BRONZE)
                .gender(Gender.MALE)
                .point(100)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        Store store = Store.builder()
                .user(user)
                .name("피자피자")
                .content("내용내용")
                .picture("사진사진")
                .rating(4.7)
                .build();

        storeRepository.save(store);

        Store findStore = storeRepository.findByName(store.getName()).get();
        assertThat(findStore).isEqualTo(store);

        List<Store> result1 = storeRepository.findAll();
        assertThat(result1).containsExactly(store);
    }

    @Test
    public void searchTest() {
        User user1 = User.builder()
                .email("test1")
                .password("1234")
                .name("kim")
                .phone("010-1111-2222")
                .address("주소1")
                .grade(Grade.BRONZE)
                .gender(Gender.MALE)
                .point(100)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user1);

        Store store1 = Store.builder()
                .user(user1)
                .name("피자피자")
                .content("내용내용")
                .picture("사진사진")
                .rating(4.7)
                .build();

        storeRepository.save(store1);

        User user2 = User.builder()
                .email("test2")
                .password("1234")
                .name("park")
                .phone("010-3333-2222")
                .address("주소2")
                .grade(Grade.BRONZE)
                .gender(Gender.MALE)
                .point(200)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user2);

        Store store2 = Store.builder()
                .user(user2)
                .name("치킨치킨")
                .content("내용2")
                .picture("사진2")
                .rating(3.1)
                .build();

        storeRepository.save(store2);

        StoreSearchCondition condition = new StoreSearchCondition();
        condition.setUserName("kim");

        List<StoreUserDto> result = storeRepository.search(condition);

        assertThat(result).extracting("storeName").containsExactly("피자피자");
    }
}