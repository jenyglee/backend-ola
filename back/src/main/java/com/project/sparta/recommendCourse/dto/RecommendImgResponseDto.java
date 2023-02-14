package com.project.sparta.recommendCourse.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RecommendImgResponseDto {
    private List<String> imgList;

    public RecommendImgResponseDto(List<String> imgList) {
        this.imgList = imgList;
    }
}
