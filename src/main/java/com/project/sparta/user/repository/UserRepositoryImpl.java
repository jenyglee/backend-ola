package com.project.sparta.user.repository;


import com.project.sparta.user.dto.QUserResponseDto;
import com.project.sparta.user.dto.UserResponseDto;
import com.project.sparta.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private QUser user = new QUser("user");

    @Override
    public UserResponseDto findByUsername(String username) {

        return jpaQueryFactory.select(new QUserResponseDto(
                        user.id,
                        user.password,
                        user.username,
                        user.age,
                        user.email,
                        user.phoneNumber,
                        user.role))
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();
    }
}
