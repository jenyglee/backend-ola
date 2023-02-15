package com.project.sparta.like.entity;

import com.project.sparta.communityComment.entity.CommunityComment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikeId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userNickName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityCommentId")
    private CommunityComment comment;

    @Builder
    public CommentLike(String userEmail, String userNickName, CommunityComment comment) {
        this.userEmail = userEmail;
        this.userNickName = userNickName;
        this.comment = comment;
    }
}
