package com.project.sparta.noticeBoard.repository;


import static com.project.sparta.noticeBoard.entity.QNoticeBoard.noticeBoard;
import static com.project.sparta.user.entity.QUser.user;

import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.dto.QNoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeBoardRepositoryImpl implements NoticeBoardRepositoryCustom {

    private final JPQLQueryFactory queryFactory;

    @Override
    public QueryResults<NoticeBoardResponseDto> findAllByCategory(NoticeCategoryEnum categoryEnum, int page, int size) {
        QueryResults<NoticeBoardResponseDto> results = queryFactory
            .select(
                new QNoticeBoardResponseDto(
                    noticeBoard.id,
                    noticeBoard.user.nickName,
                    noticeBoard.title,
                    noticeBoard.contents,
                    noticeBoard.category,
                    noticeBoard.createAt,
                    noticeBoard.modifiedAt
                ))
            .from(noticeBoard)
            .join(noticeBoard.user, user)
            .where(noticeBoard.category.eq(categoryEnum)) // 1:SERVICE, 2:EVENT, 3:UPDATE
            .offset(page)
            .limit(size)
            .fetchResults();

        return results;
    }
}
