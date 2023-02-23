package com.project.sparta.communityComment.service;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.dto.CommentResponseDto;
import com.project.sparta.user.entity.User;

public interface CommunityCommentService {
  CommentResponseDto createCommunityComments(Long boardId,CommunityRequestDto communityRequestDto,
      User user);
  void updateCommunityComments(Long communityCommentId, CommunityRequestDto communityRequestDto, User user);
  void deleteCommunityComments(Long commentsId, User user);
}
