package com.example.project.repository;

import com.example.project.domain.*;
import com.example.project.dto.UserDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.project.domain.QUser.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory = new JPAQueryFactory(em);

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

    @Test
    public void simpleProjection() {
        List<String> result = queryFactory
                .select(user.email)
                .from(user)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void tupleProjection() {
        List<Tuple> result = queryFactory
                .select(user.email, user.name)
                .from(user)
                .fetch();

        for (Tuple tuple : result) {
            String email = tuple.get(user.email);
            String username = tuple.get(user.name);
            System.out.println("email = " + email);
            System.out.println("username = " + username);
        }
    }

    @Test
    public void findDtoBySetter() {
        List<UserDto> result = queryFactory
                .select(Projections.bean(UserDto.class,
                        user.email,
                        user.name))
                .from(user)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }
}