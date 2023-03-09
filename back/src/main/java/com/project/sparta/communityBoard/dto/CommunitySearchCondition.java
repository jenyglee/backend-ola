package com.project.sparta.communityBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunitySearchCondition {
    private String title;
    private String contents;
    private String nickname;
    private Long hashtagId;
    private String chatStatus;
    private String sort;

    @Builder
    public CommunitySearchCondition(String title, String contents, String nickname, Long hashtagId, String chatStatus, String sort) {
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
        this.hashtagId = hashtagId;
        this.chatStatus = chatStatus;
        this.sort = sort;
    }
}
