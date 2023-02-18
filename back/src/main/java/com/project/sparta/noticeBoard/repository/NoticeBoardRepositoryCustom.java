package com.project.sparta.noticeBoard.repository;

import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import com.querydsl.core.QueryResults;
import java.util.List;
import org.springframework.data.domain.Page;

public interface NoticeBoardRepositoryCustom {
    QueryResults<NoticeBoardResponseDto> findAllByCategory(NoticeCategoryEnum categoryEnum, int page, int size);
}
