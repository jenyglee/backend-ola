package com.project.sparta.communityComment.dto;

import com.project.sparta.communityComment.entity.CommunityComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CommunityWithLikeResponseDto {

    private Long communityBoardId;
    private Long id;
    private String nickName;
    private String contents;
    private Long likeCount; //코멘트 좋아요

    public CommunityWithLikeResponseDto(CommunityComment communityComment ,Long likeCount) {
        this.communityBoardId = communityComment.getCommunityBoardId();
        this.id = communityComment.getId();
        this.nickName = communityComment.getNickName();
        this.contents = communityComment.getContents();
        this.likeCount = likeCount;
    }

}
