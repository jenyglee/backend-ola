package com.project.sparta.communityBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunitySearchCondition {
    private String title;
    private String contents;
    private String nickname;
    private Long hashtagId;

    public CommunitySearchCondition(String title, String contents, String nickname, Long hashtagId) {
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
        this.hashtagId = hashtagId;
    }
}
