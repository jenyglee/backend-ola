package com.project.sparta.noticeBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeBoardRequestDto {
    private String title;
    private String contents;

    public NoticeBoardRequestDto(String title,String contents){
        this.title = title;
        this.contents = contents;
    }




}
