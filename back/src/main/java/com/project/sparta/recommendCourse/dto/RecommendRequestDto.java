package com.project.sparta.recommendCourse.dto;

import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import lombok.Getter;

import java.util.List;

@Getter
public class RecommendRequestDto {

    private String title;

    private int score;

    private String season;

    private int altitude;

    private String contents;

    private List<RecommendCourseImg> imgList;

}
