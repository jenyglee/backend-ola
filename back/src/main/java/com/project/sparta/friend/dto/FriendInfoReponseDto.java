package com.project.sparta.friend.dto;

import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.UserTag;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendInfoReponseDto {

    private Long friendId;
    private String friendImage;

    private String friendName;

    private List<Hashtag> hashtagList;
    @Builder
    public FriendInfoReponseDto(Long friendId, String friendImage, String friendName) {
        this.friendId = friendId;
        this.friendImage = friendImage;
        this.friendName = friendName;
    }

    @Builder
    public FriendInfoReponseDto(Long friendId, String friendImage, String friendName, List<Hashtag> hashtagList) {
        this.friendId = friendId;
        this.friendImage = friendImage;
        this.friendName = friendName;
        this.hashtagList = hashtagList;
    }
}
