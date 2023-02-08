package com.project.sparta.communityComment.service;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import org.springframework.http.ResponseEntity;

public interface CommunityCommentService {
  public CommunityComment createComments(Long boardId, CommunityRequestDto communityRequestDto);
  public ResponseEntity deleteComments(Long commentsId);
  public ResponseEntity allDeleteComments();
  public ResponseEntity updateComments(Long boardId, CommunityRequestDto communityRequestDto);
}
