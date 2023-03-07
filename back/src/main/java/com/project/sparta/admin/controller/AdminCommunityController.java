package com.project.sparta.admin.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"어드민 커뮤니티"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminCommunityController {
//todo 어드민 권한만 들어올 수 있도록 설정추가(완료)
    private final CommunityBoardService communityBoardService;
    // 커뮤니티 전체 조회
    @ApiOperation(value = "어드민 커뮤니티 전체 조회", response = Join.class)
    @GetMapping("/boards/communities")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "path", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "보여줄 개수", required = true, dataType = "String", paramType = "query", defaultValue = "10", example = "10"),
        @ApiImplicitParam(name = "title", value = "제목", required = false, dataType = "String", paramType = "query", defaultValue = "0"),
        @ApiImplicitParam(name = "contents", value = "내용", required = false, dataType = "String", paramType = "query", defaultValue = "0"),
        @ApiImplicitParam(name = "nickname", value = "작성자명", required = false, dataType = "String", paramType = "query", defaultValue = "0"),
        @ApiImplicitParam(name = "hashtagId", value = "태그 ID", required = false, dataType = "Long", paramType = "query", example = "1"),
        @ApiImplicitParam(name = "chatStatus", value = "크루원모집 상태(Y/N)", required = false, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "정렬 기준(likeDesc/boardIdDesc)", required = false, dataType = "String", paramType = "query", defaultValue = "boardIdDesc"),
    })
    public ResponseEntity getCommunityList(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String contents,
        @RequestParam(required = false) String nickname,
        @RequestParam(required = false) Long hashtagId,
        @RequestParam(required = false) String chatStatus,
        @RequestParam(required = false) String sort) {
        PageResponseDto<List<CommunityBoardAllResponseDto>> result = communityBoardService.getAllCommunityBoard(
            page, size, title, contents, nickname, hashtagId, chatStatus, sort);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 커뮤니티 단건 조회
    @ApiOperation(value = "커뮤니티 단건 조회", response = Join.class)
    @GetMapping("/boards/communities/{boardId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "커뮤니티 ID", required = true, dataType = "Long", paramType = "path", example = "123"),
        @ApiImplicitParam(name = "commentPage", value = "댓글 페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "commentSize", value = "댓글 보여질 개수", required = true, dataType = "int", paramType = "query", example = "10")
    })
    public ResponseEntity getCommunity(@PathVariable Long boardId, @RequestParam int commentPage,
        int commentSize, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommunityBoardOneResponseDto result = communityBoardService.getCommunityBoard(boardId,
            commentPage, commentSize, userDetails.getUser().getNickName());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // 커뮤니티 수정
    @ApiOperation(value = "커뮤니티 수정", response = Join.class)
    @PatchMapping("/boards/communities/{boardId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "커뮤니티 ID", required = true, dataType = "Long", paramType = "path", example = "123"),
    })
    public ResponseEntity updateCommunity(@PathVariable Long boardId,
        @RequestBody CommunityBoardRequestDto requestDto) {
        communityBoardService.adminUpdateCommunityBoard(boardId, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 커뮤니티 삭제
    @ApiOperation(value = "커뮤니티 삭제", response = Join.class)
    @DeleteMapping("/boards/communities/{boardId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "커뮤니티 ID", required = true, dataType = "Long", paramType = "path", example = "123"),
    })
    public ResponseEntity deleteCommunity(@PathVariable Long boardId) {
        communityBoardService.adminDeleteCommunityBoard(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
