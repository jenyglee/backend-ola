package com.project.sparta.recommendCourse.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseRecommendCourseImgDto {
    private List<String> imgList;

    public ResponseRecommendCourseImgDto(List<String> imgList) {
        this.imgList = imgList;
    }
}
