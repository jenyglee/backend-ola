package com.project.sparta.recommendCourse.dto;

import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import lombok.Getter;

import java.util.List;

@Getter
public class RecommendDetailResponseDto {

    private String title;

    private String contents;

    private List<String> imgRoute;

    public RecommendDetailResponseDto(RecommendCourseBoard post, List<String> imgList) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.imgRoute = imgList;


    }
}
