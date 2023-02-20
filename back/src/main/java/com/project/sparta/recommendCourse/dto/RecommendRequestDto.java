package com.project.sparta.recommendCourse.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecommendRequestDto {

    private int score;
    private String title;
    private String season;
    private int altitude;
    private String contents;
    private String region;
    private List<String> imgList;

    @Builder
    public RecommendRequestDto(int score, String title, String season, int altitude,
        String contents, String region, List<String> imgList) {
        this.score = score;
        this.title = title;
        this.season = season;
        this.altitude = altitude;
        this.contents = contents;
        this.region = region;
        this.imgList = imgList;
    }
}
