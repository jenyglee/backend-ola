package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityComment.entity.QCommunityComment;
import com.project.sparta.like.entity.QBoardLike;
import com.project.sparta.like.entity.QCommentLike;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.project.sparta.communityBoard.entity.QCommunityBoard.*;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPQLQueryFactory queryFactory;

    public BoardRepositoryImpl(JPQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    private final QBoardLike boarLike = new QBoardLike("boardLike");

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
    public CommunityBoardResponseDto getBoard(Long boardId) {

        return queryFactory.select(Projections.constructor(CommunityBoardResponseDto.class,
                communityBoard.id.as("boardId"),
                communityBoard.user.nickName.as("nickName"),
                communityBoard.title.as("title"),
                communityBoard.contents.as("contents"),
                communityBoard.tagList.as("tagList"),
                ExpressionUtils.as(JPAExpressions.select(boarLike.board.count()).from(boarLike).groupBy(boarLike.board), "communityLikeCnt"),
                communityBoard.communityComment.as("communityComments"),
                ExpressionUtils.as(JPAExpressions.select(commentLike.comment.count()).from(commentLike).groupBy(commentLike.comment), "commentLikeCnt")))
            .from(communityBoard, communityComment)
            .join(communityBoard.communityComment, communityComment).fetchJoin()
            .join(commentLike).on(communityComment.eq(commentLike.comment)).fetchJoin()
            .join(boarLike).on(communityBoard.eq(boarLike.board)).fetchJoin()
            .where(communityBoard.id.eq(boardId))
            .orderBy(communityBoard.id.desc())
            .fetchOne();
    }

    //커뮤니티 게시글 + 커뮤니티 좋아요 + 페이징
    @Override
    public PageImpl<CommunityBoardResponseDto> communityAllList(Pageable pageable) {

        List<CommunityBoardResponseDto> boardList = queryFactory.select(Projections.constructor(CommunityBoardResponseDto.class,
                communityBoard.id.as("boardId"),
                communityBoard.user.nickName.as("nickName"),
                communityBoard.title.as("title"),
                communityBoard.tagList.as("tagList"),
                ExpressionUtils.as(JPAExpressions.select(boarLike.board.count()).from(boarLike).groupBy(boarLike.board), "communityLikeCnt")))
            .from(communityBoard, boarLike)
            .join(boarLike).on(communityBoard.eq(boarLike.board)).fetchJoin()
            .orderBy(communityBoard.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(boardList, pageable, boardList.size());
    }

}
