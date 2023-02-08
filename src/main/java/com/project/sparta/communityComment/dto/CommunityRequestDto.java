package com.project.sparta.communityComment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class CommunityRequestDto {
  Long userId;
  String userName;
  String contents;

  public CommunityRequestDto(Long userId, String userName, String contents) {
    this.userId = userId;
    this.userName = userName;
    this.contents = contents;
  }

}
