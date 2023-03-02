package com.project.sparta.communityComment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class CommunityRequestDto {

  private String contents;

  public CommunityRequestDto(String contents) {
    this.contents = contents;
  }

}
