package com.project.sparta.communityBoard.dto;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.dto.CommunityResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityBoardResponseDto {
  private String nickName;
  private String contents;
  private String title;
  private List<CommunityResponseDto> communityComments;

  private Long id;

  public CommunityBoardResponseDto(CommunityBoard communityBoard) {
    this.nickName = communityBoard.getNickName();
    this.contents = communityBoard.getContents();
    this.title = communityBoard.getTitle();
    this.id = communityBoard.getId();
    this.communityComments = communityBoard.getCommunityComment()
        .stream()
        .map(CommunityResponseDto::new)
        .collect(Collectors.toList());
  }
}
