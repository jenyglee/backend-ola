package com.project.sparta.offerCourse.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseOfferCourseDto {
    private List<String> imgList;

    public ResponseOfferCourseDto(List<String> imgList) {
        this.imgList = imgList;
    }
}
