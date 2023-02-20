package com.project.sparta.communityBoard.repository;

import static com.project.sparta.communityBoard.entity.QCommunityBoard.communityBoard;
import static com.project.sparta.communityBoard.entity.QCommunityTag.communityTag;
import static com.project.sparta.user.entity.QUser.user;

import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.QCommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.entity.QCommunityTag;
import com.project.sparta.communityComment.entity.QCommunityComment;
import com.project.sparta.like.entity.QBoardLike;
import com.project.sparta.like.entity.QCommentLike;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPQLQueryFactory queryFactory;

    public BoardRepositoryImpl(JPQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    private final QBoardLike boardLike = new QBoardLike("boardLike");

    private final QCommunityComment communityComment = new QCommunityComment("comment");

    private final QCommentLike commentLike = new QCommentLike("commLike");


    @Override
    public Long countByUserId(Long userId) {
        return queryFactory.selectFrom(communityBoard)
            .where(
                communityBoard.user.Id.eq(userId)
            )
            .fetchCount();
    }

    @Override
    public CommunityBoardOneResponseDto getBoard(Long boardId) {
        List<CommunityBoardOneResponseDto> results = queryFactory.select(
                Projections.constructor(CommunityBoardOneResponseDto.class,
                    communityBoard.title,
                    communityBoard.user.nickName,
                    communityBoard.contents,
//                    Projections.list()// img리스트 추가필요
                    Projections.list(communityTag.hashtag.name)
                )
            )
            .from(communityBoard)
            .innerJoin(communityTag).on(communityBoard.id.eq(communityTag.communityBoard.id)) // 7L 8L 9L => 3개
//            .leftJoin(communityBoard.tagList, communityTag)
            .where(communityBoard.id.eq(boardId))
            .fetch();
        results.stream().map(CommunityBoardOneResponseDto::getTagList).forEach(Hibernate::initialize);

        for (CommunityBoardOneResponseDto result : results) {
            System.out.println("result.getTitle() = " + result.getTitle());
            System.out.println("result.getNickName() = " + result.getNickName());
            System.out.println("result.getContents() = " + result.getContents());
        }

        //        List<CommunityBoardOneResponseDto> boardList = queryFactory.select(Projections.constructor(CommunityBoardOneResponseDto.class,
//                communityBoard.id.as("boardId"),
//                communityBoard.user.nickName.as("nickName"),
//                communityBoard.title.as("title"),
//                communityBoard.tagList.as("tagList"),
//                ExpressionUtils.as(JPAExpressions.select(boarLike.board.count()).from(boarLike).groupBy(boarLike.board), "communityLikeCnt")))
//            .from(communityBoard, boarLike)
//            .join(boarLike).on(communityBoard.eq(boarLike.board)).fetchJoin()
//            .orderBy(communityBoard.id.desc())
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch();
//        queryFactory.select(Projections.constructor(CommunityBoardOneResponseDto.class,
//                communityBoard.id.as("boardId"),
//                communityBoard.user.nickName.as("nickName"),
//                communityBoard.title.as("title"),
//                communityBoard.contents.as("content"),
//                communityBoard.tagList.as("tagList"),
//
//            ))
        

//        return queryFactory.select(Projections.constructor(CommunityBoardOneResponseDto.class,
//                communityBoard.id.as("boardId"),
//                communityBoard.user.nickName.as("nickName"),
//                communityBoard.title.as("title"),
//                communityBoard.contents.as("contents"),
//                communityBoard.tagList.as("tagList"),
//                ExpressionUtils.as(JPAExpressions.select(boarLike.board.count()).from(boarLike).groupBy(boarLike.board), "communityLikeCnt"),
//                communityBoard.communityComment.as("communityComments"),
//                ExpressionUtils.as(JPAExpressions.select(commentLike.comment.count()).from(commentLike).groupBy(commentLike.comment), "commentLikeCnt")))
//            .from(communityBoard, communityComment)
//            .join(communityBoard.communityComment, communityComment).fetchJoin()
//            .join(commentLike).on(communityComment.eq(commentLike.comment)).fetchJoin()
//            .join(boarLike).on(communityBoard.eq(boarLike.board)).fetchJoin()
//            .where(communityBoard.id.eq(boardId))
//            .orderBy(communityBoard.id.desc())
//            .fetchOne();
//        return results;
        return null;
    }

    //커뮤니티 게시글 + 커뮤니티 좋아요 + 페이징
    @Override
    public QueryResults<CommunityBoardAllResponseDto> communityAllList(Pageable pageable) {
        QueryResults<CommunityBoardAllResponseDto> results = queryFactory
            .select(
                new QCommunityBoardAllResponseDto(
                    communityBoard.id,
                    user.nickName,
                    communityBoard.title,
                    ExpressionUtils.as(
                        JPAExpressions.select(boardLike.board.count()).from(boardLike)
                            .where(boardLike.board.id.eq(communityBoard.id)), "communityLikeCnt")
//                    Projections.list(communityTag.hashtag), // 방법1
//                    communityTag.hashtag, // 방법2
                )
            )
            .from(communityBoard)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();
        return results;
    }

}
