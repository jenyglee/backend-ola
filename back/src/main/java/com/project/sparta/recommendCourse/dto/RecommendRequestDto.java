package com.project.sparta.recommendCourse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecommendRequestDto {

    @ApiModelProperty(example = "0")
    private int score;
    @ApiModelProperty(example = "스웨거로 전달할 제목")
    private String title;
    @ApiModelProperty(example = "스웨거로 전달할 계절")
    private String season;
    @ApiModelProperty(example = "100")
    private int altitude;
    @ApiModelProperty(example = "스웨거로 전달할 내용")
    private String contents;
    @ApiModelProperty(example = "스웨거로 전달할 산지역")
    private String region;
    @ApiModelProperty(example = "[]")
    private List<String> imgList;

    @Builder
    public RecommendRequestDto(int score, String title, String season, int altitude,
        String contents, String region, List<String> imgList) {
        this.score = score;
        this.title = title;
        this.season = season;
        this.altitude = altitude;
        this.contents = contents;
        this.region = region;
        this.imgList = imgList;
    }


}
