package com.project.sparta;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class FinalApplicationTests {
    @Autowired
    EntityManager em;
    @Test
    void contextLoads() {
        //기본 세팅 테스트
        // Hello hello = new Hello();
        // em.persist(hello);
        //
        // Hello hello1 = em.find(Hello.class, hello.getId());
        // Assertions.assertThat(hello).isEqualTo(hello1);

        //querydsl 세팅 테스트
//        Hello hello = new Hello();
//        em.persist(hello);
//        JPQLQueryFactory queryFactory = new JPAQueryFactory(em);
//
//        Hello hello1 = queryFactory.selectFrom(QHello.hello)
//                .fetchOne();
//
//        Assertions.assertThat(hello).isEqualTo(hello1);
    }

}
