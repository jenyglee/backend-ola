package com.project.sparta.recommendCourse.dto;

import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import lombok.Getter;

import java.util.List;

@Getter
public class RecommendDetailResponseDto {

    private String nickName;
    private String title;

    private int score;
    private String season;
    private int altitude;

    private String contents;

    private List<String> imgList;

    public RecommendDetailResponseDto(RecommendCourseBoard post, List<String> imgList, String nickName) {
        this.nickName = nickName;
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.score = post.getScore();
        this.season = post.getSeason();
        this.altitude = post.getAltitude();
        this.imgList = imgList;


    }
}
