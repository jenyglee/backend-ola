package com.project.sparta.communityBoard.dto;

import com.project.sparta.hashtag.entity.Hashtag;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityBoardRequestDto {

    private String contents;
    private String title;
    private String chatStatus;
    private int chatMemCnt;

    private List<Long> tagList;
    private List<String> imgList;

    @Builder
    public CommunityBoardRequestDto(String contents, String title, String chatStatus, int chatMemCnt, List<Long> tagList, List<String> imgList) {
        this.contents = contents;
        this.title = title;
        this.chatStatus = chatStatus;
        this.chatMemCnt = chatMemCnt;
        this.tagList = tagList;
        this.imgList = imgList;
    }
}
