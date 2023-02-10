package com.project.sparta.offerCourse.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseRecommandCourseImgDto {
    private List<String> imgList;

    public ResponseRecommandCourseImgDto(List<String> imgList) {
        this.imgList = imgList;
    }
}
