package com.project.sparta.friend.service;


import com.project.sparta.user.dto.UserResponseDto;

public interface FriendService {

    void addFriend(Long userId, String targetUsername); //친구 추가
    void deleteFriend(String targetUsername);    // 친구 삭제

}
