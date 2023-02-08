package com.project.sparta.member.service;

import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
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
        User user1 = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg","Y");
        User user2 = new User("1234", "한세인", 20, "010-1234-1234", "user2@naver.com", UserRoleEnum.USER, "user2.jpg","Y");
        User user3 = new User("1234", "김민선", 30, "010-1234-1234", "user3@naver.com", UserRoleEnum.USER, "user3.jpg","Y");
        User user4 = new User("1234", "김주성", 20, "010-1234-1234", "user4@naver.com", UserRoleEnum.USER, "user4.jpg","Y");
        User user5 = new User("1234", "김두영", 30, "010-1234-1234", "user5@naver.com", UserRoleEnum.USER, "user5.jpg","Y");
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
                                .where(user.userName.eq("user1"))
                                .fetchOne();

        assertThat(findUser.getUserName()).isEqualTo("user1");
    }
}