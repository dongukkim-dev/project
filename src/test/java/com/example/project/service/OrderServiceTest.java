package com.example.project.service;

import com.example.project.domain.Gender;
import com.example.project.domain.Grade;
import com.example.project.domain.Role;
import com.example.project.domain.User;
import com.example.project.repository.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @SpringBootTest 를 사용하면 통합 테스트 (실제 객체들로 테스트 진행)
 */
@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    /**
     * asd
     */
    @Test
    void addOrder() {
        //given
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

        //when

        //then
    }

    @Test
    void findById() {
    }

    @Test
    void findAllByStore() {
    }

    @Test
    void findLastOrderByStore() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void delete() {
    }
}