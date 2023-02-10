package com.project.sparta.user.repository;


import com.project.sparta.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustomerRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private QUser user = new QUser("user");

}
