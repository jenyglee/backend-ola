package com.project.sparta.friend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendSearchReponseDto {

    private String friendImage;

    private String friendName;

    @Builder
    public FriendSearchReponseDto(String friendImage, String friendName) {
        this.friendImage = friendImage;
        this.friendName = friendName;
    }
}
