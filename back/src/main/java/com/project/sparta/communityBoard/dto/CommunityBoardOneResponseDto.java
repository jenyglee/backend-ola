package com.project.sparta.communityBoard.dto;


import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityBoardOneResponseDto {
    private String title;
    private String nickName;
    private String contents;
//    private List<String> imgList;
    private List<String> tagList = new ArrayList<>(); // TODO List<HashtagDto>로 변환필요
//    private List<String> commentList;  // TODO List<CommentDto>로 변환필요

//    private int boardLikeCount;

//    private LocalDateTime createdAt;

//    @Builder
    public CommunityBoardOneResponseDto(String title, String nickName, String contents,
//        List<String> imgList,
        List<String> tagList
//        List<String> commentList,
//        int boardLikeCount,
//        LocalDateTime createdAt
    ) {
        this.title = title;
        this.nickName = nickName;
        this.contents = contents;
//        this.imgList = imgList;
        this.tagList = tagList;
//        this.commentList = commentList;
//        this.boardLikeCount = boardLikeCount;
//        this.createdAt = createdAt;
    }

}
