package com.project.sparta.recommendCourse.dto;

import lombok.Getter;

@Getter
public class RecommendRequestDto {

    private String title;

    private int score;

    private String season;

    private int altitude;

    private String contents;

}
