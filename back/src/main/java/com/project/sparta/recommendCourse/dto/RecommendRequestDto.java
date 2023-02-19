package com.project.sparta.recommendCourse.dto;

import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecommendRequestDto {

    private String title;
    private int score;
    private String season;
    private int altitude;
    private String contents;
    private List<String> imgList;

    @Builder
    public RecommendRequestDto(String title, int score, String season, int altitude,
        String contents,
        List<String> imgList) {
        this.title = title;
        this.score = score;
        this.season = season;
        this.altitude = altitude;
        this.contents = contents;
        this.imgList = imgList;
    }
}
