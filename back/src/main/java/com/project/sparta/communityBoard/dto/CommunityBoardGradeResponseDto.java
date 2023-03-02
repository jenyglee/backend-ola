package com.project.sparta.communityBoard.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityBoardGradeResponseDto {

  private int maniaResponse;
  private int godResponse;

  public CommunityBoardGradeResponseDto(int maniaResponse, int godResponse) {
    this.maniaResponse = maniaResponse;
    this.godResponse = godResponse;
  }
}
