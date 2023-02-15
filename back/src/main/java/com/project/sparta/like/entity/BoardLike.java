package com.project.sparta.like.entity;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardLikesId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userNickName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_board_id")
    private CommunityBoard board;

    @Builder
    public BoardLike(String userEmail, String userNickName, CommunityBoard board) {
        this.userEmail = userEmail;
        this.userNickName = userNickName;
        this.board = board;
    }
}
