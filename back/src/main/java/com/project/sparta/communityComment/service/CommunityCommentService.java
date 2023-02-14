package com.project.sparta.communityComment.service;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.dto.CommunityResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface CommunityCommentService {
  CommunityResponseDto createCommunityComments(Long boardId,CommunityRequestDto communityRequestDto,
      User user);
  CommunityResponseDto updateCommunityComments(Long boardId, Long communityCommentId, CommunityRequestDto communityRequestDto,
      User user);
  void deleteCommunityComments(Long boardId, Long commentsId,User user);
}
