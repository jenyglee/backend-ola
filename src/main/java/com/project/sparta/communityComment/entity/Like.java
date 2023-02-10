package com.project.sparta.communityComment.entity;

public class Like {

  private Long userId;
  private Long communityCommentId;
  private Long likeCount;

  public Like(Long userId, Long communityCommentId) {
    this.userId = userId;
    this.communityCommentId = communityCommentId;
  }

}
