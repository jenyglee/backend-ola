package com.project.sparta.friend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendInfoReponseDto {

    private String friendImage;

    private String friendName;

    @Builder
    public FriendInfoReponseDto(String friendImage, String friendName) {
        this.friendImage = friendImage;
        this.friendName = friendName;
    }
}
