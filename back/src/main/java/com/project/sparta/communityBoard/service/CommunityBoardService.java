package com.project.sparta.communityBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import java.util.List;

public interface CommunityBoardService {
   CommunityBoardResponseDto createCommunityBoard(CommunityBoardRequestDto communityBoardRequestDto, User user);
   CommunityBoardResponseDto updateCommunityBoard(Long community_board_id, CommunityBoardRequestDto communityBoardRequestDto, User user);

   void deleteCommunityBoard(Long community_board_id);


   CommunityBoardResponseDto getCommunityBoard(Long communityBoardId);

   List<CommunityBoardResponseDto> getAllCommunityBoard(int page, int size);

   List<CommunityBoardResponseDto> getMyCommunityBoard(int page, int size,  User user);


}
