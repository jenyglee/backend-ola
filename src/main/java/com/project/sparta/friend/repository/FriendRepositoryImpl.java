package com.project.sparta.friend.repository;

import com.project.sparta.user.dto.UserResponseDto;
import com.project.sparta.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendCustomRepository {

    //queryDsl 사용
    private final JPAQueryFactory queryFactory;

    QUser user = new QUser("user");

    @Override
    public UserResponseDto serachFriend() {
        return null;
    }
}
