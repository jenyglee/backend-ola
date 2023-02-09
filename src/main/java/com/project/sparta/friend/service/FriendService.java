package com.project.sparta.friend.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.dto.FriendSearchReponseDto;

import java.util.List;

public interface FriendService {

    void addFriend(Long userId, String targetUsername); //친구 추가
    void deleteFriend(String targetUsername);    // 친구 삭제
    PageResponseDto<List<FriendSearchReponseDto>> searchFriend(int offset, int limit, String targetUsername);    //친구 검색

}
