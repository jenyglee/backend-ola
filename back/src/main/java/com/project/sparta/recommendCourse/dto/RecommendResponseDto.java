package com.project.sparta.recommendCourse.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendResponseDto {

    private Long boardId;
    private String title;
    private String thumbnail;
    private List<String> imgList;
    private LocalDateTime createDate;
    private Long likeCount;
    private String nickName;

    @Builder
    public RecommendResponseDto(Long boardId, String title, String thumbnail, List<String> imgList,
        LocalDateTime createDate, Long likeCount, String nickName) {
        this.boardId = boardId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.imgList = imgList;
        this.createDate = createDate;
        this.likeCount = likeCount;
        this.nickName = nickName;
    }

    @Builder
    public RecommendResponseDto(Long boardId, String title, String thumbnail,
        Long likeCount, String nickName, LocalDateTime createDate) {
        this.boardId = boardId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.likeCount = likeCount;
        this.nickName = nickName;
        this.createDate = createDate;
    }

}
