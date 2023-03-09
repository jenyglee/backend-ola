package com.project.sparta.friend.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.dto.FriendInfoReponseDto;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface FriendService {

    PageResponseDto<List<FriendInfoReponseDto>> AllMyFriendList(int page, int size, Long userId);    //내 친구 전체조회

    PageResponseDto<List<FriendInfoReponseDto>> AllRecommendFriendList(int page, int size, Long userId);  //추천 친구 전체조회

    void addFriend(Long userId, Long targetId); //친구 추가
    void deleteFriend(Long userId, Long targetId);    // 친구 삭제
    PageResponseDto<List<FriendInfoReponseDto>> searchFriend(int page, int size, String targetUsername);    //친구 검색(내친구 포함 모든친구)

}
