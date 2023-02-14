package com.project.sparta.communityComment.dto;

import com.project.sparta.communityComment.entity.CommunityComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityResponseDto {

  private Long communityBoardId;
  private Long id;
  private String nickName;
  private String contents;

  @Builder
  public CommunityResponseDto(CommunityComment communityComment) {
    this.communityBoardId = communityComment.getCommunityBoardId();
    this.id = communityComment.getId();
    this.nickName = communityComment.getNickName();
    this.contents = communityComment.getContents();
  }
}
