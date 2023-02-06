package com.project.sparta.member.service;

import com.project.sparta.member.entity.Member;
import com.project.sparta.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.project.sparta.member.entity.MemberRoleEnum.CUSTOMER;
import static com.project.sparta.member.entity.MemberRoleEnum.SELLER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberServiceImplTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    QMember m = new QMember("m");
    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @BeforeEach
    public void Befor() {
        Member member1 = new Member("1234", "member1", 10, "010-1234-1234", "member1@naver.com", CUSTOMER);
        Member member2 = new Member("1234", "member2", 20, "010-1234-1235", "member2@naver.com", CUSTOMER);
        Member member3 = new Member("1234", "member3", 30, "010-1234-1236", "member3@naver.com", CUSTOMER);
        Member member4 = new Member("1234", "member4", 40, "010-1234-1237", "member4@naver.com", SELLER);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //초기화
        em.flush();
        em.clear();
    }

    @Test
    public void findMemberQueryDsl() {
        Member findMember = queryFactory
                                .selectFrom(m)
                                .where(m.username.eq("member1"))
                                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
}