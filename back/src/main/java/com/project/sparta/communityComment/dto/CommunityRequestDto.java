package com.project.sparta.communityComment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class CommunityRequestDto {

  @ApiModelProperty(example = "스웨거로 전달할 내용")
  private String contents;

  public CommunityRequestDto(String contents) {

    this.contents = contents;
  }

}
