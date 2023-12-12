package com.example.project.repository.user;

import com.example.project.domain.QUser;
import com.example.project.domain.User;
import com.example.project.dto.UserSearchCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.project.domain.QUser.*;

@Repository
public class UserJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(User user) {
        em.persist(user);
    }

    public Optional<User> findById(Long id) {
        User findUser = em.find(User.class, id);
        return Optional.ofNullable(findUser);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();

    }

    public List<User> findAll_Querydsl() {
        return queryFactory
                .selectFrom(user)
                .fetch();
    }

    public List<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<User> findByEmail_Querydsl(String email) {
        return queryFactory
                .selectFrom(user)
                .where(user.email.eq(email))
                .fetch();
    }
}
