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
    @Column(name ="noticeBoard_id")
    private Long id;

    private String nickName;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;


    public NoticeBoard(String nickName, String title, String contents) {
        this.nickName = nickName;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents){
        this.title = title;
        this.contents = contents;
    }

}
