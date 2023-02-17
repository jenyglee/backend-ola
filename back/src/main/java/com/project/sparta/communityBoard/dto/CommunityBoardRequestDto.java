package com.project.sparta.communityBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class CommunityBoardRequestDto {
  private String contents;
  private String title;

  @Builder
  public CommunityBoardRequestDto(String contents, String title) {
    this.contents = contents;
    this.title = title;
  }

}
