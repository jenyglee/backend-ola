package com.project.sparta.noticeBoard.entity;

import com.project.sparta.admin.entity.Timestamped;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class NoticeBoard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="NoticeBoard_ID")
    private Long id;

    private String username;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;



    public NoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto){

        this.title = noticeBoardRequestDto.getTitle();
        this.contents = noticeBoardRequestDto.getTitle();

    }

    public void update(NoticeBoardRequestDto noticeBoardRequestDto){
        this.title = noticeBoardRequestDto.getTitle();
        this.contents = noticeBoardRequestDto.getContents();
    }



}
