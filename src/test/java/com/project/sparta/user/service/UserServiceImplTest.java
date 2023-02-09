package com.project.sparta.user.service;

import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
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
        User user1 = new User("user1@naver.com","1234", "이재원", UserRoleEnum.USER, USER_REGISTERED, 10,"010-1234-1234","sdf.jpg",
            UserGradeEnum.MOUNTAIN_GOD);

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