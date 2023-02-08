package com.project.sparta.friend.repository;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.Tag;
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
    public Page<User> recommentFriendSearch(List<Tag> tagList, Pageable pageable, StatusEnum statusEnum) {
        List<User> recommentFriend = queryFactory.selectFrom(user)
                                .where(user.tags.contains(tagList.get(0))
                                    .or(user.tags.contains(tagList.get(1)))
                                    .or(user.tags.contains(tagList.get(2)))
                                    .or(user.tags.contains(tagList.get(3)))
                                    .or(user.tags.contains(tagList.get(4)))
                                    .and(user.status.eq(statusEnum)))
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetch();

        return new PageImpl<>(recommentFriend, pageable, recommentFriend.size());
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
