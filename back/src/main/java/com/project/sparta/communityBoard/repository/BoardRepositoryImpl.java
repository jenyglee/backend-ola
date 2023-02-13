package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.entity.QCommunityBoard;
import com.project.sparta.user.entity.QUser;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.stereotype.Repository;
import static com.project.sparta.communityBoard.entity.QCommunityBoard.*;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom{
    private final JPQLQueryFactory queryFactory;

    public BoardRepositoryImpl(JPQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Long countByUserId(Long userId) {
        return queryFactory.selectFrom(communityBoard)
                .where(
                        communityBoard.user.Id.eq(userId)
                )
                .fetchCount();
    }
}
