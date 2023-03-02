package com.project.sparta.communityBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CommunityBoardService {

    CommunityBoard createCommunityBoard(CommunityBoardRequestDto communityBoardRequestDto, User user);

    void updateCommunityBoard(Long boardId,
        CommunityBoardRequestDto communityBoardRequestDto, User user);

    void deleteCommunityBoard(Long boardId, User user);

    CommunityBoardOneResponseDto getCommunityBoard(Long boardId, int commentPage, int commentSize, String nickname);
    PageResponseDto<List<CommunityBoardAllResponseDto>> getCacheAllCommunityBoard(int page, int size,
        String titleCond, String contentsCond, String nicknameCond);
    PageResponseDto<List<CommunityBoardAllResponseDto>> getAllCommunityBoard(int page, int size,
        String titleCond, String contentsCond, String nicknameCond);

    PageResponseDto<List<CommunityBoardAllResponseDto>> getMyCommunityBoard(int page, int size, User user);

//       PageResponseDto<List<CommunityBoardOneResponseDto>> getMyCommunityBoard(int page, int size,  User user);
    void adminUpdateCommunityBoard(Long boardId, CommunityBoardRequestDto requestDto);

    void adminDeleteCommunityBoard(Long boardId);

    PageResponseDto<List<CommunityBoardAllResponseDto>> getMyChatBoardList(int page, int size, Long userId);

    CommunityBoardOneResponseDto getBoard(Long boardId);
}
