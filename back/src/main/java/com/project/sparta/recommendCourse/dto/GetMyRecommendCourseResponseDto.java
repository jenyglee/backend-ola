package com.project.sparta.recommendCourse.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetMyRecommendCourseResponseDto {
    private String title;
    private LocalDateTime localDateTime;

    private String nickName;

    private Long likeCount;

    @Builder
    public GetMyRecommendCourseResponseDto(String title, LocalDateTime localDateTime, String nickName, Long likeCount) {
        this.title = title;
        this.localDateTime = localDateTime;
        this.nickName = nickName;
        this.likeCount = likeCount;
    }
}
