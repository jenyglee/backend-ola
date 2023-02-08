package com.project.sparta.communityBoard.service;

import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.security.UserDetailImpl;

public interface CommunityBoardService {
  public CommunityBoard createCommunityBoard(CommunityBoardRequestDto communityBoardRequestDto, String nickName);
  public CommunityBoard updateCommunityBoard(Long boardId, CommunityRequestDto communityRequestDto, String nickName);

  public void deleteCommunityBoard(Long boardId);

  public void deleteAllCommunityBoard();


  }
