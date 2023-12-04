package com.example.project.repository;

import com.example.project.domain.Gender;
import com.example.project.domain.Grade;
import com.example.project.domain.Role;
import com.example.project.domain.User;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserJpaRepository userJpaRepository;

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

        userJpaRepository.save(user);

        User findUser = userJpaRepository.findByEmail_Querydsl(user.getEmail()).get(0);
        assertThat(findUser).isEqualTo(user);

        List<User> result1 = userJpaRepository.findAll_Querydsl();
        assertThat(result1).containsExactly(user);
    }
}