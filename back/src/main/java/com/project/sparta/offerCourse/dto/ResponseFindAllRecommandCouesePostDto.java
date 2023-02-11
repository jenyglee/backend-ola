package com.project.sparta.offerCourse.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseFindAllRecommandCouesePostDto {
    private String title;
    private List<String> imgList;

    //나중에 좋아요 개수 추가해야 함.

    @Builder
    public ResponseFindAllRecommandCouesePostDto(String title, List<String> imgList) {
        this.title = title;
        this.imgList = imgList;
    }
}
