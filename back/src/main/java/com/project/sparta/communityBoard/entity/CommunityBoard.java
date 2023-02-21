package com.project.sparta.communityBoard.entity;

import com.project.sparta.common.entity.Timestamped;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@AllArgsConstructor
public class CommunityBoard extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "community_board_id")
    private Long id;

    private String title;   //채팅방 이름&게시글 제목

    private String contents;  //내용
    @OneToMany
    private List<Hashtag> tagList = new ArrayList<>();  //채팅방 태그리스트
    private String chatStatus = "N";
    private int chatMemCnt = 0;

    @OneToMany(mappedBy = "communityBoardId", cascade = CascadeType.REMOVE)
    private List<CommunityComment> communityComment = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public CommunityBoard(String title, String contents, List<Hashtag> tagList, String chatStatus,
        int chatMemCnt, List<CommunityComment> communityComment, User user
    ) {
        this.title = title;
        this.contents = contents;
        this.tagList = tagList;
        this.chatStatus = chatStatus;
        this.chatMemCnt = chatMemCnt;
        this.communityComment = communityComment;
        this.user = user;
    }

    public void updateBoard(String title, String contents, List<Hashtag> tagList) {
        this.title = title;
        this.contents = contents;
        this.tagList = tagList;
    }
}
