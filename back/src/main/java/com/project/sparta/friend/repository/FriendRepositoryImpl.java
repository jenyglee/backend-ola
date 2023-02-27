package com.project.sparta.friend.repository;

import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.entity.QFriend;
import com.project.sparta.hashtag.entity.QHashtag;
import com.project.sparta.user.entity.StatusEnum;
import com.project.sparta.user.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
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

    QHashtag hashtag = new QHashtag("Hashtag");

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

        // 현재 회원을 뺀 가입 상태인 사용자의 태그 리스트 추출
        // 현재 회원의 태그와 맞는 회원 추출
        List<User> userList = queryFactory.select(user)
            .from(userTag)
            .leftJoin(userTag.user, user)
            .where(user.status.eq(statusEnum),           // 활동중인 유저
                user.Id.ne(userInfo.getId()),           //현재 로그인한 유저 정보 아닌거
                builder,                                //현재 나랑 하나라도 맞는 태그를 가진 유저
                fbuilder)                               //내 친구가 아닌 유저
            .orderBy(NumberExpression.random().asc())
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
