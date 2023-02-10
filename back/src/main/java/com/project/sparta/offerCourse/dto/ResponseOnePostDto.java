package com.project.sparta.offerCourse.dto;

import com.project.sparta.offerCourse.entity.RecommandCoursePost;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseOnePostDto{

    private String title;

    private String contents;

    private List<String> imgRoute;

    public ResponseOnePostDto(RecommandCoursePost post, List<String> imgList) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.imgRoute = imgList;


    }
}
