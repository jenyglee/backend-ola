package com.project.sparta.noticeBoard.entity;

import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class NoticeBoard  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="NoticeBoard_ID")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;


    @Builder
    public NoticeBoard( String title, String contents){

        this.title = title;
        this.contents = contents;
    }
    public void update(NoticeBoardRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }



}
