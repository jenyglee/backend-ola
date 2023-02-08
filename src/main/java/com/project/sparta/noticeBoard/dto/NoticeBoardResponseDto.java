package com.project.sparta.noticeBoard.dto;

import com.project.sparta.noticeBoard.entity.NoticeBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeBoardResponseDto {
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public NoticeBoardResponseDto(NoticeBoard notice){
        this.title = notice.getTitle();
        this.contents = notice.getContents();
        this.modifiedAt = getModifiedAt();
        this.createdAt = getCreatedAt();
    }
}
