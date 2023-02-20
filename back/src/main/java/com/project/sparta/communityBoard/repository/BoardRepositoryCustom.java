package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Long countByUserId(Long userId);
    CommunityBoardResponseDto getBoard(Long boardId);
    Page<CommunityBoardResponseDto> communityAllList(Pageable pageable);
}