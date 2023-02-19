package com.project.sparta.recommendCourse.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendResponseDto {

    private String title;
    private List<String> imgList;
    private LocalDateTime createDate;

    //TODO: 나중에 좋아요 개수 추가해야 함. => OK
    private Long likeCount;
    private String nickName;

    @Builder
    public RecommendResponseDto(String title, List<String> imgList,
        Long likeCount, String nickName, LocalDateTime createDate) {
        this.title = title;
        this.imgList = imgList;
        this.likeCount = likeCount;
        this.nickName = nickName;
        this.createDate = createDate;
    }
}
