package com.project.sparta.admin.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.service.NoticeBoardService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = {"어드민 공지사항 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminNoticeController {
    private final NoticeBoardService noticeBoardService;

    //공지사항 작성
    @ApiOperation(value = "공지사항 작성",response = Join.class)
    @PostMapping("/boards/notices")
    public ResponseEntity createNotice(@RequestBody NoticeBoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.createNoticeBoard(requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지사항 삭제
    @ApiOperation(value = "공지사항 삭제",response = Join.class)
    @DeleteMapping("/boards/notices/{boardId}")
    public ResponseEntity deleteNotice(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.deleteNoticeBoard(boardId, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지사항 수정
    @ApiOperation(value = "공지사항 수정",response = Join.class)
    @PatchMapping("/boards/notices/{boardId}")
    public ResponseEntity updateNotice(@PathVariable Long boardId,
        @RequestBody NoticeBoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.updateNoticeBoard(boardId, requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지글 단건조회
    @ApiOperation(value = "공지사항 단건조회",response = Join.class)
    @GetMapping("/boards/notices/{boardId}")
    public ResponseEntity getNoticeBoard(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(boardId,
            userDetails.getUser());
        return new ResponseEntity(noticeBoard, HttpStatus.OK);
    }

    //공지사항 전체조회
    @ApiOperation(value = "공지사항 전체조회",response = Join.class)
    @GetMapping("/boards/notices")
    public ResponseEntity getNoticeBoardList(@RequestParam int page,
        @RequestParam int size,
        @RequestParam String category,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(
            page, size, category, userDetails.getUser());
        return new ResponseEntity(allNoticeBoard, HttpStatus.OK);
    }
}
