package com.project.sparta.communityBoard.entity;

import com.project.sparta.common.entity.Timestamped;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
    @Column(name = "community_board_id")
    private Long id;

    private String title;   //채팅방 이름&게시글 제목

    private String contents;  //내용
//    @OneToMany
//    private List<Hashtag> tagList = new ArrayList<>();  //채팅방 태그리스트
    @OneToMany(mappedBy = "communityBoard", cascade = CascadeType.REMOVE)
    private List<CommunityTag> tagList = new ArrayList<>();

    @OneToMany(mappedBy = "communityBoard", cascade = CascadeType.REMOVE)
    private List<CommunityBoardImg> imgList = new ArrayList<>();

    private String chatStatus;
    private int chatMemCnt;
    private int maniaResponse;
    private int godResponse;
    private int currentMemCnt;


    //TODO @OneToMany를 빼는 방식으로(게시물이 존재하지 않으면 댓글도 없음)
    @OneToMany(mappedBy = "communityBoardId", cascade = CascadeType.REMOVE)
    private List<CommunityComment> communityComment = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public CommunityBoard(String title, String contents, String chatStatus, int chatMemCnt,
        User user , int maniaResponse, int godResponse) {
        this.title = title;
        this.contents = contents;
        this.imgList = imgList;
        this.chatStatus = chatStatus;
        this.chatMemCnt = chatMemCnt;
        this.user = user;
        this.maniaResponse = maniaResponse;
        this.godResponse = godResponse;
    }

    public void updateCommunityTag(List<CommunityTag> tagList) {
        this.tagList = tagList;
    }

    public void updateCommunityImg(List<CommunityBoardImg> imgList) {
        this.imgList = imgList;
    }

    public void updateBoard(String title, String contents, List<CommunityTag> tagList,
        List<CommunityBoardImg> imgList, String chatStatus, int chatMemCnt) {
        this.title = title;
        this.contents = contents;
        this.tagList = tagList;
        this.imgList = imgList;
        this.chatStatus = chatStatus;
        this.chatMemCnt = chatMemCnt;
    }

    public void updateCurrentMemCnt(int currentMemCnt){
        this.currentMemCnt = currentMemCnt;
    }
    public void set_maniaResponse(int maniaResponse){
        this.maniaResponse = maniaResponse;
    }

    public void set_godResponse(int godResponse) {
        this.godResponse = godResponse;
    }

}
