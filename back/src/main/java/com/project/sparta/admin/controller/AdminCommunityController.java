package com.project.sparta.admin.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"어드민 커뮤니티 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminCommunityController {
//todo 어드민 권한만 들어올 수 있도록 설정추가(완료)
    private final CommunityBoardService communityBoardService;

    // 커뮤니티 전체 조회
    @GetMapping("/boards/communities")
    public ResponseEntity getCommunityList(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String title,
        @RequestParam String contents,
        @RequestParam String nickname,
        @RequestParam Long hashtagId,
        @RequestParam String chatStatus,
        @RequestParam String sort) {
        PageResponseDto<List<CommunityBoardAllResponseDto>> result = communityBoardService.getAllCommunityBoard(
            page, size, title, contents, nickname, hashtagId, chatStatus, sort);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    // 커뮤니티 단건 조회
    @GetMapping("/boards/communities/{boardId}")
    public ResponseEntity getCommunity(@PathVariable Long boardId, @RequestParam int commentPage,
        int commentSize, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommunityBoardOneResponseDto result = communityBoardService.getCommunityBoard(boardId,
            commentPage, commentSize, userDetails.getUser().getNickName());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // 커뮤니티 수정
    @PatchMapping("/boards/communities/{boardId}")
    public ResponseEntity updateCommunity(@PathVariable Long boardId,
        @RequestBody CommunityBoardRequestDto requestDto) {
        communityBoardService.adminUpdateCommunityBoard(boardId, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 커뮤니티 삭제
    @DeleteMapping("/boards/communities/{boardId}")
    public ResponseEntity deleteCommunity(@PathVariable Long boardId) {
        communityBoardService.adminDeleteCommunityBoard(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
