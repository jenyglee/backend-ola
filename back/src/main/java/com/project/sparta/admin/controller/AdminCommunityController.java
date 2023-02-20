package com.project.sparta.admin.controller;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"어드민 커뮤니티 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCommunityController {
    private final CommunityBoardService communityBoardService;

    // 커뮤니티 전체 조회
    @GetMapping("/boards/communities")
    public ResponseEntity getCommunityList(@RequestParam int page, @RequestParam int size){
        PageResponseDto<List<CommunityBoardAllResponseDto>> result = communityBoardService.getAllCommunityBoard(
            page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    // 커뮤니티 단건 조회
    @GetMapping("/boards/communities/{boardId}")
    public ResponseEntity getCommunity(@PathVariable Long boardId){
        CommunityBoardOneResponseDto result = communityBoardService.getCommunityBoard(boardId);
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
