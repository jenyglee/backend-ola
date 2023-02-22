package com.project.sparta.friend.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.service.FriendService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"친구추천 API"})
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friends")
    public ResponseEntity AllMyFriendList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @AuthenticationPrincipal UserDetailsImpl user) {

        PageResponseDto<List<FriendInfoReponseDto>> friendList = friendService.AllMyFriendList(page,
            size, user.getUser().getId());

        return new ResponseEntity(friendList, HttpStatus.OK);
    }

    @GetMapping("/friends/recommends")
    public ResponseEntity AllRecomentFriendList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @AuthenticationPrincipal UserDetailsImpl user) {

        PageResponseDto<List<FriendInfoReponseDto>> friendList = friendService.AllRecomentFriendList(
            page, size, user.getUser().getId());

        return new ResponseEntity(friendList, HttpStatus.OK);
    }

    @PostMapping("/friends")
    public ResponseEntity addFriend(@AuthenticationPrincipal UserDetailsImpl user,
        @RequestParam(name = "targetId") Long targetId) {
        //친구 추가
        friendService.addFriend(user.getUser().getId(), targetId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/friends")
    public ResponseEntity deleteFriend(@RequestParam(name = "targetId") Long targetId,
        @AuthenticationPrincipal UserDetailsImpl user) {
        //친구 삭제
        friendService.deleteFriend(user.getUser().getId(), targetId);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/friend")
    public ResponseEntity searchFriend(@RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "targetNickname") String targetNickname) {
        PageResponseDto<List<FriendInfoReponseDto>> searchFriend = friendService.searchFriend(page, size, targetNickname);
        return new ResponseEntity(searchFriend, HttpStatus.OK);
    }
}
