package com.project.sparta.friend.repository;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.user.entity.*;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendCustomRepository {

    private final JPAQueryFactory queryFactory;

    QUser user = new QUser("user");

    @Override
    public List<User> randomUser(StatusEnum statusEnum) {
        // 현재 회원을 뺀 가입 상태인 사용자의 태그 리스트 추출
        // => 랜덤으로 100명 잘라서 그 안에서 매칭할 수 있도록 성능개선해야 함.
        return queryFactory.selectFrom(user)
                .where(user.status.eq(statusEnum))
                .orderBy(NumberExpression.random().asc())
                .limit(5)
                .fetch();
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
