package com.project.sparta.communityBoard.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetMyBoardResponseDto {
    private String nickName;
    private String title;

    private Long likeCount;
    private LocalDateTime localDateTime;

    @Builder
    public GetMyBoardResponseDto(String nickName, String title, Long likeCount, LocalDateTime localDateTime) {
        this.nickName = nickName;
        this.title = title;
        this.likeCount = likeCount;
        this.localDateTime = localDateTime;
    }
}
