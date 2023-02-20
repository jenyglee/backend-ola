package com.project.sparta.friend.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.service.FriendService;
import com.project.sparta.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor 
public class FriendController {

    private final FriendService friendService;

    //TODO 내 친구목록 전체조회 API 제작(GET "/friends")
    @GetMapping("/friends")
    public ResponseEntity AllMyFriendList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @AuthenticationPrincipal UserDetailsImpl user) {

        PageResponseDto<List<FriendInfoReponseDto>> friendList = friendService.AllMyFriendList(page,
            size, user.getUser().getId());

        return new ResponseEntity(friendList, HttpStatus.OK);
    }

    //TODO 추천 친구목록 전체 조회(태그기준) API 제작(GET "/friends/recommends")
    @GetMapping("/friends/recommends")
    public ResponseEntity AllRecomentFriendList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @AuthenticationPrincipal UserDetailsImpl user) {

        PageResponseDto<List<FriendInfoReponseDto>> friendList = friendService.AllRecomentFriendList(
            page, size, user.getUser().getId());

        return new ResponseEntity(friendList, HttpStatus.OK);
    }

    //TODO 친구 추가 API 제작(POST "/friends/{target_id}")
    @PostMapping("/friends/{target_id}")
    public ResponseEntity addFriend(@AuthenticationPrincipal UserDetailsImpl user,
        @PathVariable(name = "targetFriend") String targetFriend) {
        friendService.addFriend(user.getUser().getId(), targetFriend);
        return new ResponseEntity(HttpStatus.OK);
    }

    //TODO 친구 삭제 API 제작(DELETE "/friends/{target_id}")
    @DeleteMapping("/friends/{targetId}")
    public ResponseEntity deleteFriend(@PathVariable(name = "targetId") Long targetId) {
        friendService.deleteFriend(targetId);
        return new ResponseEntity(HttpStatus.OK);
    }

    //TODO 친구 검색(내친구 포함 모든친구) API 제작 (GET "/friends/{target_nickname}")
    @GetMapping("/friends/{targetNickname}")
    public ResponseEntity searchFriend( @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "targetNickname") String targetNickname) {
        PageResponseDto<List<FriendInfoReponseDto>> searchFriend = friendService.searchFriend(page, size, targetNickname);
        return new ResponseEntity(searchFriend, HttpStatus.OK);
    }
}
