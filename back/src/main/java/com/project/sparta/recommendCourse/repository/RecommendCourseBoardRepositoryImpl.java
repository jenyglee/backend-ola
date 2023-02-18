package com.project.sparta.recommendCourse.repository;


import com.project.sparta.like.entity.QCourseLike;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.PostStatusEnum;
import com.project.sparta.recommendCourse.entity.QRecommendCourseBoard;
import com.project.sparta.recommendCourse.entity.QRecommendCourseImg;
import com.project.sparta.user.entity.QUser;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class RecommendCourseBoardRepositoryImpl implements RecommendCourseBoardCustomRepository {

    private final JPAQueryFactory queryFactory;

    QUser user = new QUser("user");

    QRecommendCourseBoard reBoard = new QRecommendCourseBoard("reBoard");

    QCourseLike cLike = new QCourseLike("cLike");

    QRecommendCourseImg courseImg = new QRecommendCourseImg("courseImg");

    @Override
    public Page<RecommendResponseDto> allRecommendBoardList(PageRequest pageable,
        PostStatusEnum postStatusEnum) {

        List<RecommendResponseDto> boards = queryFactory
            .select(Projections.constructor(RecommendResponseDto.class,
                reBoard.title.as("title"),
                Projections.list(courseImg.url),
                ExpressionUtils.as(JPAExpressions.select(cLike.courseBoard.count()).from(cLike).where(cLike.courseBoard.id.eq(reBoard.id)), "likeCount"),
                user.nickName.as("nickName"),
                reBoard.modifiedAt.as("createDate")))
            .from(reBoard)
            .leftJoin(user).on(reBoard.userId.eq(user.Id))
            .leftJoin(cLike).on(reBoard.id.eq(cLike.courseBoard.id))
            .leftJoin(courseImg).on(reBoard.id.eq(courseImg.recommendCourseBoard.id))
            .where(reBoard.postStatus.eq(postStatusEnum))
            .orderBy(reBoard.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(boards, pageable, boards.size());
    }
}

