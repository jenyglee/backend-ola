package com.project.sparta.noticeBoard.entity;

import com.project.sparta.admin.entity.Timestamped;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@Getter
public class NoticeBoard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="notice_board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private NoticeCategoryEnum category;


    public NoticeBoard(User user, String title, String contents, NoticeCategoryEnum category) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.category = category;
    }

    public void update(String title, String contents, NoticeCategoryEnum category){
        this.title = title;
        this.contents = contents;
        this.category = category;
    }

}
