package com.project.sparta.friend.service;


import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.dto.RecommentFriendResponseDto;

import java.util.List;

public interface FriendService {

    PageResponseDto<List<FriendInfoReponseDto>> AllMyFriendList(int offset, int limit, Long userId);    //내 친구 전체조회

    PageResponseDto<List<FriendInfoReponseDto>> AllRecomentFriendList(int offset, int limit, Long userId);  //추천 친구 전체조회

    void addFriend(Long userId, String targetUsername); //친구 추가
    void deleteFriend(Long targetId);    // 친구 삭제
    PageResponseDto<List<FriendInfoReponseDto>> searchFriend(int offset, int limit, String targetUsername);    //친구 검색(내친구 포함 모든친구)

}
