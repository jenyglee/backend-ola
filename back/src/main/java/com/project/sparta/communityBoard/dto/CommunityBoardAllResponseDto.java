package com.project.sparta.communityBoard.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityBoardAllResponseDto {
    private Long boardId;
    private String nickName;
    private String title;
    private Long communityLikeCnt;
    private List<String> imgList;

    @Builder
    public CommunityBoardAllResponseDto(Long boardId, String nickName, String title,
        Long communityLikeCnt,
        List<String> imgList
    ) {
        this.boardId = boardId;
        this.nickName = nickName;
        this.title = title;
        this.communityLikeCnt = communityLikeCnt;
        this.imgList = imgList;
    }
}
