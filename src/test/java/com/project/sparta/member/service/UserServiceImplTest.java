package com.project.sparta.member.service;

import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.project.sparta.user.entity.UserRoleEnum.ADMIN;
import static com.project.sparta.user.entity.UserRoleEnum.USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
//@Rollback(value = false)
class UserServiceImplTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    QUser user = new QUser("user");
    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @BeforeEach
    public void Befor() {
        User user1 = new User("1234", "user1", 10, "010-1234-1234", "user1@naver.com", USER);
        User user2 = new User("1234", "user2", 20, "010-1234-1235", "user2@naver.com", USER);
        User user3 = new User("1234", "user3", 30, "010-1234-1236", "user3@naver.com", USER);
        User user4 = new User("1234", "user4", 40, "010-1234-1237", "user4@naver.com", ADMIN);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);

        //초기화
        em.flush();
        em.clear();
    }

    @Test
    public void findMemberQueryDsl() {
        User findUser = queryFactory
                                .selectFrom(user)
                                .where(user.username.eq("member1"))
                                .fetchOne();

        assertThat(findUser.getUsername()).isEqualTo("member1");
    }
}