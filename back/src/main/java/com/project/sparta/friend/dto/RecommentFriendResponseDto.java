package com.project.sparta.friend.dto;

import com.project.sparta.hashtag.entity.Hashtag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommentFriendResponseDto implements Comparable<RecommentFriendResponseDto>{

    private FriendInfoReponseDto friendInfoReponseDto;
    private List<Hashtag> taglist;

    private int matchingSize;

    public RecommentFriendResponseDto(FriendInfoReponseDto friendInfoReponseDto, List<Hashtag> taglist, int matchingSize) {
        this.friendInfoReponseDto = friendInfoReponseDto;
        this.taglist = taglist;
        this.matchingSize = matchingSize;
    }

    @Override
    public int compareTo(RecommentFriendResponseDto o) {

        if(o.matchingSize < matchingSize){
            return 1;
        }else if(o.matchingSize > matchingSize){
            return -1;
        }
        return 0;
    }
}
