package com.project.sparta.communityBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.security.UserDetailImpl;
import com.project.sparta.user.entity.User;
import java.util.List;

public interface CommunityBoardService {
   CommunityBoardResponseDto createCommunityBoard(CommunityBoardRequestDto communityBoardRequestDto, User user);
   CommunityBoardResponseDto updateCommunityBoard(CommunityRequestDto communityRequestDto, User user);

   void deleteCommunityBoard(User user);

   void deleteAllCommunityBoard();

   CommunityBoardResponseDto getCommunityBoard(User user);

   PageResponseDto<List<CommunityBoardResponseDto>> getAllCommunityBoard(int page, int size);

   public PageResponseDto<List<CommunityBoardResponseDto>> getMyCommunityBoard(int page, int size, User user);


}
