package com.project.sparta.friend.repository;

import com.project.sparta.friend.entity.QFriend;
import com.project.sparta.hashtag.entity.QHashtag;
import com.project.sparta.user.entity.StatusEnum;
import com.project.sparta.user.entity.*;
import com.querydsl.core.BooleanBuilder;
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

    QUserTag userTag = new QUserTag("userTag");

    QFriend friend = new QFriend("friend");

    @Override
    public PageImpl<User> randomUser(User userInfo, Pageable pageable, StatusEnum statusEnum) {

        BooleanBuilder builder = new BooleanBuilder();
        BooleanBuilder fbuilder = new BooleanBuilder();

        //1. 내 친구 리스트 뽑기
        List<Long> friendList = queryFactory.select(friend.targetId)
                                            .from(friend)
                                            .where(friend.userId.eq(userInfo.getId()))
                                            .fetch();

        for (int i = 0; i < userInfo.getTags().size(); i++) {
            if (userInfo.getTags().get(i) != null) {
                builder.or(userTag.tag.in(userInfo.getTags().get(i).getTag()));
            }
        }

        for (int j = 0; j < friendList.size(); j++) {
            if (!friendList.isEmpty()) {
                fbuilder.and(user.Id.ne(friendList.get(j)));
            }
        }

        List<User> userList = queryFactory.select(user)
            .from(userTag)
            .leftJoin(userTag.user, user)
            .where(user.status.eq(statusEnum),
                user.Id.ne(userInfo.getId()),
                builder,
                fbuilder)
            .distinct()
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


        getUserTagList(userList);

        return new PageImpl<>(userList, pageable, userList.size());
    }

    @Override
    public PageImpl<User> serachFriend(String targetUserName, Pageable pageable) {
        List<User> userList = queryFactory.select(user)
            .from(user)
            .where(user.nickName.startsWith(targetUserName))
            .orderBy(user.nickName.desc())
            .distinct()
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        getUserTagList(userList);

        return new PageImpl<>(userList, pageable, userList.size());
    }


    public void getUserTagList(List<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            List<UserTag> tags = queryFactory.select(userTag)
                .from(userTag)
                .where(userTag.user.Id.eq(userList.get(i).getId())).fetch();
            userList.get(i).getUserTagList(tags);
        }
    }
}
