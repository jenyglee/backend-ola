package com.project.sparta.recommendCourse.repository;


import com.project.sparta.like.entity.QCourseLike;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.PostStatusEnum;
import com.project.sparta.recommendCourse.entity.QRecommendCourseBoard;
import com.project.sparta.recommendCourse.entity.QRecommendCourseImg;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.QUserTag;
import com.project.sparta.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    QUserTag userTag = new QUserTag("userTag");

    //코스 전체 조회(검색 조건 제외)
    @Override
    public Page<RecommendResponseDto> allRecommendBoardList(PageRequest pageable,
        PostStatusEnum postStatusEnum,
        int score, String season, int altitude, String region, UserDetailsImpl userInfo) {

        List<RecommendResponseDto> boards = queryFactory
            .select(Projections.constructor(RecommendResponseDto.class,
                reBoard.title.as("title"),
                Projections.list(courseImg.url),
                ExpressionUtils.as(JPAExpressions.select(cLike.courseBoard.count()).from(cLike)
                    .where(cLike.courseBoard.id.eq(reBoard.id)), "likeCount"),
                user.nickName.as("nickName"),
                reBoard.modifiedAt.as("createDate")))
            .from(reBoard)
            .leftJoin(user).on(reBoard.userId.eq(user.Id))
            .leftJoin(cLike).on(reBoard.id.eq(cLike.courseBoard.id))
            .leftJoin(courseImg).on(reBoard.id.eq(courseImg.recommendCourseBoard.id))
            .where(reBoard.postStatus.eq(postStatusEnum),
                    userTag.user.Id.eq(userInfo.getUser().getId()),
                    userTag.tag.in(userInfo.getUser().getTags().get(0).getTag(),
                        userInfo.getUser().getTags().get(1).getTag(),
                        userInfo.getUser().getTags().get(2).getTag(),
                        userInfo.getUser().getTags().get(3).getTag(),
                        userInfo.getUser().getTags().get(4).getTag()))
            .orderBy(reBoard.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(boards, pageable, boards.size());
    }







    //BooleanExpression은 null 반환 시 자동으로 조건절에서 제거 된다.
    //(단, 모든 조건이 null이 발생 시 전체 엔티티를 불러오게 되므로 대장애가 발생할 수 있음)
    private BooleanExpression eqSeason(String season) {
        if (StringUtils.isEmpty(season)) {
            return null;
        }
        return reBoard.season.contains(season);
    }

    private BooleanExpression eqRegion(String region) {
        if (StringUtils.isEmpty(region)) {
            return null;
        }
        return reBoard.region.contains(region);
    }
}

//    List<RecommendResponseDto> boards = queryFactory
//        .select(Projections.constructor(RecommendResponseDto.class,
//            reBoard.title.as("title"),
//            Projections.list(courseImg.url),
//            ExpressionUtils.as(JPAExpressions.select(cLike.courseBoard.count()).from(cLike)
//                .where(cLike.courseBoard.id.eq(reBoard.id)), "likeCount"),
//            user.nickName.as("nickName"),
//            reBoard.modifiedAt.as("createDate")))
//        .from(reBoard)
//        .leftJoin(user).on(reBoard.userId.eq(user.Id))
//        .leftJoin(cLike).on(reBoard.id.eq(cLike.courseBoard.id))
//        .leftJoin(courseImg).on(reBoard.id.eq(courseImg.recommendCourseBoard.id))
//        .where(reBoard.postStatus.eq(postStatusEnum),
//            reBoard.score.loe(score)
//                .or(eqSeason(season))
//                .or(reBoard.altitude.loe(altitude))
//                .or(eqRegion(region)))
//        .orderBy(reBoard.id.desc())
//        .offset(pageable.getOffset())
//        .limit(pageable.getPageSize())
//        .fetch();
