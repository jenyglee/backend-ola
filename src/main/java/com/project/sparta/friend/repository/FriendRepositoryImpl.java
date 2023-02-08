package com.project.sparta.friend.repository;

import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendCustomRepository {

    //queryDsl 사용
    private final JPAQueryFactory queryFactory;

    QUser user = new QUser("user");

    @Override
    public PageImpl<User> serachFriend(String targetUserName, Pageable pageable) {
        List<User> userList = queryFactory.select(user)
                                .from(user)
                                .where(user.name.eq(targetUserName))
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetch();
        return new PageImpl<>(userList, pageable, userList.size());
    }
}
