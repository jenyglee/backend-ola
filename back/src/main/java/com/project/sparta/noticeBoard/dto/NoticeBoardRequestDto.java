package com.project.sparta.noticeBoard.dto;

import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class NoticeBoardRequestDto {
    @ApiModelProperty(example = "스웨거로 전달할 제목")
    private String title;
    @ApiModelProperty(example = "스웨거로 전달할 컨텐츠")
    private String contents;
    @ApiModelProperty(example = "스웨거로 전달할 카테고리")
    private NoticeCategoryEnum category;

    @Builder
    public NoticeBoardRequestDto(String title, String contents, NoticeCategoryEnum category) {
        this.title = title;
        this.contents = contents;
        this.category = category;
    }
}
