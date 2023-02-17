package com.project.sparta.communityBoard.dto;

import com.project.sparta.hashtag.entity.Hashtag;
import java.util.ArrayList;
import java.util.List;
import com.project.sparta.communityComment.entity.CommunityComment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CommunityBoardResponseDto {
    private Long boardId;
    private String nickName;
    private String title;
    private String contents;
    private List<Hashtag> tagList = new ArrayList<>();
    private Long communityLikeCnt;
    private List<CommunityComment> communityComments;
    private Long commentLikeCnt;

    @Builder
    public CommunityBoardResponseDto(Long boardId, String nickName, String title, String contents,
        List<Hashtag> tagList, Long communityLikeCnt, List<CommunityComment> communityComments,
        Long commentLikeCnt) {
        this.boardId = boardId;
        this.nickName = nickName;
        this.title = title;
        this.contents = contents;
        this.tagList = tagList;
        this.communityLikeCnt = communityLikeCnt;
        this.communityComments = communityComments;
        this.commentLikeCnt = commentLikeCnt;
    }


    public CommunityBoardResponseDto(Long boardId, String nickName, String title,
        List<Hashtag> tagList, Long communityLikeCnt) {
        this.boardId = boardId;
        this.nickName = nickName;
        this.title = title;
        this.tagList = tagList;
        this.communityLikeCnt = communityLikeCnt;
    }

//    @Builder
//    public CommunityBoardResponseDto(Long boardId, String nickName, String title, String contents,
//        List<Hashtag> tagList, Long communityLikeCnt, List<CommunityComment> communityComments,
//        Long commentLikeCnt) {
//        this.boardId = boardId;
//        this.nickName = nickName;
//        this.title = title;
//        this.contents = contents;
//        this.tagList = tagList;
//        this.communityLikeCnt = communityLikeCnt;
//        this.communityComments = communityComments;
//        this.commentLikeCnt = commentLikeCnt;
//    }
}
