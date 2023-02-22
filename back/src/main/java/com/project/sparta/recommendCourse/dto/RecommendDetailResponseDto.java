package com.project.sparta.recommendCourse.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class RecommendDetailResponseDto {

    private Long boardId;
    private int score;
    private String title;
    private String season;
    private int altitude;
    private String contents;
    private String region;
    private List<String> imgList;
    private LocalDateTime createDate;
    private Long likeCount;
    private String nickName;

    @Builder
    public RecommendDetailResponseDto(Long boardId, int score, String title, String season, int altitude,
        String contents,
        String region, List<String> imgList, LocalDateTime createDate, Long likeCount,
        String nickName) {
        this.boardId = boardId;
        this.score = score;
        this.title = title;
        this.season = season;
        this.altitude = altitude;
        this.contents = contents;
        this.region = region;
        this.imgList = imgList;
        this.createDate = createDate;
        this.likeCount = likeCount;
        this.nickName = nickName;
    }

}
