package com.project.sparta.user.service;

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

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;
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

        User user1 = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg",USER_REGISTERED);
        User user2 = new User("1234", "한세인", 20, "010-1234-1235", "user2@naver.com", UserRoleEnum.USER, "user2.jpg",USER_REGISTERED);
        User user3 = new User("1234", "김민선", 30, "010-1234-1236", "user3@naver.com", UserRoleEnum.USER, "user3.jpg",USER_REGISTERED);
        User user4 = new User("1234", "김주성", 20, "010-1234-1237", "user4@naver.com", UserRoleEnum.USER, "user4.jpg",USER_REGISTERED);
        User user5 = new User("1234", "김두영", 30, "010-1234-1238", "user5@naver.com", UserRoleEnum.USER, "user5.jpg",USER_REGISTERED);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(user5);

        //초기화
        em.flush();
        em.clear();
    }

    @Test
    public void findMemberQueryDsl() {
        User findUser = queryFactory
                                .selectFrom(user)
                                .where(user.nickName.eq("이재원"))
                                .fetchOne();

        assertThat(findUser.getNickName()).isEqualTo("이재원");
    }
}