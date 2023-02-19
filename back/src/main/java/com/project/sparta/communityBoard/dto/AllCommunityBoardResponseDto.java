package com.project.sparta.communityBoard.dto;


import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class AllCommunityBoardResponseDto {

    private final Long boardLikeCount;
    private final LocalDateTime timestamped;
    private final String title;
    private final String nickName;

    @Builder
    public AllCommunityBoardResponseDto(Long boardLikeCount, LocalDateTime timestamped, String title, String nickName) {
        this.boardLikeCount = boardLikeCount;
        this.timestamped = timestamped;
        this.title = title;
        this.nickName = nickName;
    }

}
