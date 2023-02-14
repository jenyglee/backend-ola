package com.project.sparta.recommendCourse.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RecommendResponseDto {
    private String title;
    private List<String> imgList;

    //TODO: 나중에 좋아요 개수 추가해야 함.
    private int count;

    @Builder
    public RecommendResponseDto(String title, List<String> imgList, int count) {
        this.title = title;
        this.imgList = imgList;
        this.count = count;
    }
}
