package com.project.sparta.recommendCourse.dto;

import lombok.Getter;

@Getter
public class RequestRecommendCoursePostDto {

    private String title;

    private int score;

    private String season;

    private int altitude;

    private String contents;

}
