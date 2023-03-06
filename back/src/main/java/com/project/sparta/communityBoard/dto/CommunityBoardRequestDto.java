package com.project.sparta.communityBoard.dto;

import com.project.sparta.hashtag.entity.Hashtag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityBoardRequestDto {
    @ApiModelProperty(example = "스웨거로 전달할 내용")
    private String contents;
    @ApiModelProperty(example = "스웨거로 전달할 제목")
    private String title;
    @ApiModelProperty(example = "Y")
    private String chatStatus;
    @ApiModelProperty(example = "10")
    private int chatMemCnt;
    @ApiModelProperty(example = "[1,2]")
    private List<Long> tagList;
    @ApiModelProperty(example = "[]")
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
