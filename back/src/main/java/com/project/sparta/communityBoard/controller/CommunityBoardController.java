package com.project.sparta.communityBoard.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"커뮤니티 보드 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommunityBoardController {

    private final CommunityBoardService communityBoardService;

    //커뮤니티 작성
    @ApiOperation(value = "커뮤니티 작성",response = Join.class)
    @PostMapping("/communities")
    public ResponseEntity createCommunityBoard(
        @RequestBody CommunityBoardRequestDto communityBoardRequestDto
        , @AuthenticationPrincipal UserDetailsImpl userDetail) {
        communityBoardService.createCommunityBoard(communityBoardRequestDto, userDetail.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //커뮤니티 단건 조회
    @ApiOperation(value = "커뮤니티 단건 조회",response = Join.class)
    @GetMapping("/communities/{boardId}")
    public ResponseEntity getCommunityBoard(@PathVariable Long boardId, @RequestParam int commentPage, int commentSize) {
        CommunityBoardOneResponseDto CommunityBoardOneResponseDto = communityBoardService.getCommunityBoard(
            boardId, commentPage, commentSize);
        return new ResponseEntity<>(CommunityBoardOneResponseDto, HttpStatus.OK);
    }

    // TODO 커뮤니티 전체 조회 -> 필터링 기능 구현
    //커뮤니티 전체 조회
    @ApiOperation(value = "커뮤니티 전체 조회",response = Join.class)
    @GetMapping("/communities")
    public ResponseEntity getAllCommunityBoard(@RequestParam("page") int page, @RequestParam("size") int size,
        @RequestParam String title, @RequestParam String contents, @RequestParam String nickname) {
        PageResponseDto<List<CommunityBoardAllResponseDto>> CommunityBoardOneResponseDto = communityBoardService.getAllCommunityBoard(
            page, size, title, contents, nickname);
        return new ResponseEntity<>(CommunityBoardOneResponseDto, HttpStatus.OK);
    }

    //커뮤니티 수정
    @ApiOperation(value = "커뮤니티 수정",response = Join.class)
    @PatchMapping("/communities/{boardId}")
    public ResponseEntity updateCommunityBoard(@PathVariable Long boardId,@RequestBody CommunityBoardRequestDto communityBoardRequestDto
                                                                        , @AuthenticationPrincipal UserDetailsImpl userDetail) {
        communityBoardService.updateCommunityBoard(boardId, communityBoardRequestDto,
            userDetail.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //커뮤니티 삭제
    @ApiOperation(value = "커뮤니티 삭제",response = Join.class)
    @DeleteMapping("/communities/{boardId}")
    public ResponseEntity deleteCommunityBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetail) {
        communityBoardService.deleteCommunityBoard(boardId, userDetail.getUser());
        return new ResponseEntity("보드 삭제 완료", HttpStatus.OK);
    }
}
