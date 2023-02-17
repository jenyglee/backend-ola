package com.project.sparta.noticeBoard.dto;

import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeBoardRequestDto {
    private String title;
    private String contents;
    private NoticeCategoryEnum category;

    @Builder
    public NoticeBoardRequestDto(String title, String contents, NoticeCategoryEnum category) {
        this.title = title;
        this.contents = contents;
        this.category = category;
    }
}
