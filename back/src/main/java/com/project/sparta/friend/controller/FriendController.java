package com.project.sparta.friend.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.service.FriendService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
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
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"친구"})
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @ApiOperation(value = "내 친구 전체 조회",response = Join.class)
    @GetMapping("/friends")
    public ResponseEntity AllMyFriendList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl user) {

        PageResponseDto<List<FriendInfoReponseDto>> friendList = friendService.AllMyFriendList(page,
            size, user.getUser().getId());

        return new ResponseEntity(friendList, HttpStatus.OK);
    }

    @ApiOperation(value = "추천 친구 전체 조회",response = Join.class)
    @GetMapping("/friends/recommends")
    public ResponseEntity AllRecomentFriendList(
        @RequestParam(name = "page") int page,
        @RequestParam(name = "size") int size,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl user) {

        PageResponseDto<List<FriendInfoReponseDto>> friendList = friendService.AllRecomentFriendList(
            page, size, user.getUser().getId());

        return new ResponseEntity(friendList, HttpStatus.OK);
    }

    @ApiOperation(value = "친구 추가",response = Join.class)
    @PostMapping("/friends")
    public ResponseEntity addFriend(
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl user,
        @RequestParam(name = "targetId") Long targetId) {
        friendService.addFriend(user.getUser().getId(), targetId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "친구 삭제",response = Join.class)
    @DeleteMapping("/friends")
    public ResponseEntity deleteFriend(
        @RequestParam(name = "targetId") Long targetId,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl user) {
        friendService.deleteFriend(user.getUser().getId(), targetId);
        return new ResponseEntity(HttpStatus.OK);
    }


    @ApiOperation(value = "친구 검색",response = Join.class)
    @GetMapping("/friend")
    public ResponseEntity searchFriend(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "targetNickname") String targetNickname) {
        PageResponseDto<List<FriendInfoReponseDto>> searchFriend = friendService.searchFriend(page, size, targetNickname);
        return new ResponseEntity(searchFriend, HttpStatus.OK);
    }
}
