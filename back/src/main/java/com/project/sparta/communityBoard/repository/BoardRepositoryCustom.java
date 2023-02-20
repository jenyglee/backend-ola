package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Long countByUserId(Long userId);
    CommunityBoardOneResponseDto getBoard(Long boardId);
    QueryResults<CommunityBoardAllResponseDto> communityAllList(Pageable pageable);
}