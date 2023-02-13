package com.project.sparta.offerCourse.repository;

import com.project.sparta.offerCourse.entity.QRecommandCoursePost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static com.project.sparta.communityBoard.entity.QCommunityBoard.communityBoard;
import static com.project.sparta.offerCourse.entity.QRecommandCoursePost.*;

@Repository
public class RecommendCoursePostRepositoryImpl implements RecommendCoursePostCustomRepository{
    private final JPAQueryFactory queryFactory;

    public RecommendCoursePostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    // @Override
    // public Long countByUserId(Long userId) {
    //     return queryFactory.selectFrom(recommandCoursePost)
    //             .where(
    //                     recommandCoursePost.user.Id.eq(userId)
    //             )
    //             .fetchCount();
    // }
}
