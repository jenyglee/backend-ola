package com.project.sparta.friend.repository;

import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendCustomRepository {

    //queryDsl 사용
    private final JPAQueryFactory queryFactory;

    QUser user = new QUser("user");


    /*
    * @Override
    public PageImpl<Board> getAll(Pageable pageable) {
        List<Board> boardList = queryFactory.select(board)
                .from(board)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(boardList, pageable, boardList.size());
    }
    */

    @Override
    public Page<User> serachFriend(String targetUserName, Pageable pageRequest) {
//        return queryFactory.select(user)
//                .from(user)
//                .where(user.userName.eq(targetUserName))
//                .offset()

        return null;
    }
}
