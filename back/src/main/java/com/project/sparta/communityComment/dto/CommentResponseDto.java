package com.project.sparta.communityComment.dto;

import com.project.sparta.communityComment.entity.CommunityComment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    //  private Long communityBoardId;
    private Long id;
    private String nickName;
    private String contents;
    private LocalDateTime createAt;
    private Long likeCount; //코멘트 좋아요

    //  @Builder
//  public CommunityResponseDto(CommunityComment communityComment,Long likeCount) {
//    this.communityBoardId = communityComment.getCommunityBoardId();
//    this.id = communityComment.getId();
//    this.nickName = communityComment.getNickName();
//    this.contents = communityComment.getContents();
//    this.likeCount = likeCount;
//  }
    @Builder
    public CommentResponseDto(Long id, String nickName, String contents, LocalDateTime createAt
        , Long likeCount
    ) {
        this.id = id;
        this.nickName = nickName;
        this.contents = contents;
        this.createAt = createAt;
        this.likeCount = likeCount;
    }
}
