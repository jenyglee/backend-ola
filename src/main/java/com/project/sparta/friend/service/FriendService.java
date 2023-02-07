package com.project.sparta.friend.service;


import com.project.sparta.friend.dto.FriendSearchResultDto;
import com.project.sparta.user.dto.UserResponseDto;

public interface FriendService {

    void addFriend(Long userId, String targetUsername); //친구 추가
    void deleteFriend(String targetUsername);    // 친구 삭제
    FriendSearchResultDto searchFriend(int offset, int limit, String targetUsername);    //친구 검색
}
