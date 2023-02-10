package com.project.sparta.friend.repository;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.user.entity.*;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendCustomRepository {

    private final JPAQueryFactory queryFactory;

    QUser user = new QUser("user");

    QUserTag userTag = new QUserTag("userTag");

    @Override
    public PageImpl<User> randomUser(User userinfo, Pageable pageable, StatusEnum statusEnum) {

        // 현재 회원을 뺀 가입 상태인 사용자의 태그 리스트 추출
        // 현재 회원의 태그와 맞는 회원 추출
        List<User> userList = queryFactory
                .selectFrom(user)
                .join(user).on(userTag.userId.eq(user.Id))
                .where(user.status.eq(statusEnum),
                        user.Id.ne(userinfo.getId()),
                        userTag.hashTagId.in(userinfo.getTags().get(0).getId(),
                                userinfo.getTags().get(1).getId(),
                                userinfo.getTags().get(2).getId(),
                                userinfo.getTags().get(3).getId(),
                                userinfo.getTags().get(4).getId()))
                .orderBy(NumberExpression.random().asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(userList, pageable, userList.size());
    }

    @Override
    public PageImpl<User> serachFriend(String targetUserName, Pageable pageable) {
        List<User> userList = queryFactory.select(user)
                .from(user)
                .where(user.nickName.startsWith(targetUserName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(userList, pageable, userList.size());
    }
}
