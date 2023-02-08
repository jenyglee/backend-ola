package com.project.sparta.noticeBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public class NoticeBoardRequestDto {
    private String title;
    private String contents;

}
