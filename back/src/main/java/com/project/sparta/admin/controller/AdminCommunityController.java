package com.project.sparta.admin.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCommunityController {
    private final CommunityBoardService communityBoardService;

    // 커뮤니티 전체 조회
    @GetMapping("/boards/communities")
    public ResponseEntity getCommunityList(@RequestParam int page, @RequestParam int size){
        PageResponseDto<List<CommunityBoardResponseDto>> result = communityBoardService.getAllCommunityBoard(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    // 커뮤니티 단건 조회
    @GetMapping("/boards/communities/{boardId}")
    public ResponseEntity getCommunity(@PathVariable Long boardId){
        CommunityBoardResponseDto result = communityBoardService.getCommunityBoard(boardId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // 커뮤니티 수정
    @PatchMapping("/boards/communities/{boardId}")
    public ResponseEntity updateCommunity(@PathVariable Long boardId, @RequestBody CommunityBoardRequestDto requestDto){
        communityBoardService.adminUpdateCommunityBoard(boardId, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 커뮤니티 삭제
    @DeleteMapping("/boards/communities/{boardId}")
    public ResponseEntity deleteCommunity(@PathVariable Long boardId){
        communityBoardService.adminDeleteCommunityBoard(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
