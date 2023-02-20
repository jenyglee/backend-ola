package com.project.sparta.recommendCourse.repository;


import com.project.sparta.like.entity.QCourseLike;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.PostStatusEnum;
import com.project.sparta.recommendCourse.entity.QRecommendCourseBoard;
import com.project.sparta.recommendCourse.entity.QRecommendCourseImg;
import com.project.sparta.user.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


    //코스 전체 조회(+필터링)
    @Override
    public Page<RecommendResponseDto> allRecommendBoardList(PageRequest pageable,
        PostStatusEnum postStatusEnum, int score, String season, int altitude, String region,
        String orderByLike) {

        BooleanBuilder builder = new BooleanBuilder();
        StringPath aliasLike = Expressions.stringPath("likeCount");

        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderByLike, aliasLike);

        if (score > 0) {
            builder.and(reBoard.score.loe(score));
        }
        if (season != null) {
            builder.and(reBoard.season.contains(season));
        }
        if (altitude > 0) {
            builder.and(reBoard.altitude.loe(altitude));
        }
        if (region != null) {
            builder.and(reBoard.region.contains(region));
        }

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
            .where(reBoard.postStatus.eq(postStatusEnum), builder)
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(boards, pageable, boards.size());
    }

    //코스 단건 조회
    @Override
    public RecommendDetailResponseDto getCourseBoard(Long boardId, PostStatusEnum postStatusEnum) {
       return queryFactory.select(Projections.constructor(RecommendDetailResponseDto.class,
                reBoard.score,
                reBoard.title,
                reBoard.season,
                reBoard.altitude,
                reBoard.contents,
                reBoard.region,
                Projections.list(courseImg.url),
                reBoard.modifiedAt,
                ExpressionUtils.as(JPAExpressions.select(cLike.courseBoard.count()).from(cLike)
                   .where(cLike.courseBoard.id.eq(reBoard.id)), "likeCount"),
                user.nickName))
            .from(reBoard)
            .leftJoin(user).on(reBoard.userId.eq(user.Id))
            .leftJoin(cLike).on(reBoard.id.eq(cLike.courseBoard.id))
            .leftJoin(courseImg).on(reBoard.id.eq(courseImg.recommendCourseBoard.id))
            .where(reBoard.postStatus.eq(postStatusEnum), reBoard.id.eq(boardId))
            .fetchOne();
    }

    //좋아요 필터링
    private OrderSpecifier[] createOrderSpecifier(String orderByLike, StringPath aliasLike) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if(orderByLike.equals("all")){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, reBoard.id));
        }else if(orderByLike.equals("likeDesc")){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, aliasLike));
        }else if(orderByLike.equals("likeAsc")){
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, aliasLike));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}

