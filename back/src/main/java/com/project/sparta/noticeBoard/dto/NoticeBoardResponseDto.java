package com.project.sparta.noticeBoard.dto;

import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeBoardResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private NoticeCategoryEnum category;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public NoticeBoardResponseDto(Long id, String username, String title, String contents, NoticeCategoryEnum category, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
