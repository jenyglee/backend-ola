package com.project.sparta.recommendCourse.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseFindAllRecommendCouesePostDto {
    private String title;
    private List<String> imgList;

    //나중에 좋아요 개수 추가해야 함.

    @Builder
    public ResponseFindAllRecommendCouesePostDto(String title, List<String> imgList) {
        this.title = title;
        this.imgList = imgList;
    }
}
