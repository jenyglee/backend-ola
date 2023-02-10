package com.project.sparta.communityComment.service;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.dto.CommunityResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.security.UserDetailImpl;
import com.project.sparta.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface CommunityCommentService {

  public CommunityResponseDto createCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      User user);

  public CommunityResponseDto updateCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      User user);

  public void deleteCommunityComments(Long commentsId);

  public void allDeleteCommunityComments();

}
