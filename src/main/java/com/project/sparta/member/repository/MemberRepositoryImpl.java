package com.project.sparta.member.repository;

import com.project.sparta.member.dto.MemberResponseDto;
import com.project.sparta.member.dto.QMemberResponseDto;
import com.project.sparta.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private QMember member = new QMember("member");

    @Override
    public MemberResponseDto findByUsername(String username) {

        return jpaQueryFactory.select(new QMemberResponseDto(
                                                member.id,
                                                member.password,
                                                member.username,
                                                member.age,
                                                member.email,
                                                member.phoneNumber,
                                                member.role))
                .from(member)
                .where(member.username.eq(username))
                .fetchOne();
    }
}
