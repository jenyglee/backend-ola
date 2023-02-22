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

    private Long boardId;
    private String title;
//    private String imgList;
    private List<String> imgList;
    private LocalDateTime createDate;
    private Long likeCount;
    private String nickName;

    @Builder
    public RecommendResponseDto(Long boardId, String title, List<String> imgList,
        Long likeCount, String nickName, LocalDateTime createDate) {
        this.boardId = boardId;
        this.title = title;
        this.imgList = imgList;
        this.likeCount = likeCount;
        this.nickName = nickName;
        this.createDate = createDate;
    }

//    @Builder
//    public RecommendResponseDto(Long boardId, String title, String imgList,
//        Long likeCount, String nickName, LocalDateTime createDate) {
//        this.boardId = boardId;
//        this.title = title;
//        this.imgList = imgList;
//        this.likeCount = likeCount;
//        this.nickName = nickName;
//        this.createDate = createDate;
//    }
}
