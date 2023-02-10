package com.project.sparta.communityBoard.entity;

public class Like {

  private Long userId;

  private Long communityBoardId;
  private Long likeCount;

  public Like(Long userId, Long communityBoardId) {
    this.userId = userId;
    this.communityBoardId = communityBoardId;
    this.likeCount +=1;
  }




}
