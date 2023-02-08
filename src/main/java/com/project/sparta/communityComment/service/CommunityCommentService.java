package com.project.sparta.communityComment.service;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.security.UserDetailImpl;
import org.springframework.http.ResponseEntity;

public interface CommunityCommentService {

  public CommunityComment createCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      String nickName);

  public CommunityComment updateCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      String nickName);

  public void deleteCommunityComments(Long commentsId);

  public void allDeleteCommunityComments();

}
