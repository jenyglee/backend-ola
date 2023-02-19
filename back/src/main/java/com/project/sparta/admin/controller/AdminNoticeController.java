package com.project.sparta.admin.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.service.NoticeBoardService;
import com.project.sparta.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminNoticeController {

    private final NoticeBoardService noticeBoardService;

    //공지사항 작성
    @PostMapping("/boards/notices")
    public ResponseEntity createNotice(@RequestBody NoticeBoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.createNoticeBoard(requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지사항 삭제
    @DeleteMapping("/boards/notices/{boardId}")
    public ResponseEntity deleteNotice(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.deleteNoticeBoard(boardId, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지사항 수정
    @PatchMapping("/boards/notices/{boardId}")
    public ResponseEntity updateNotice(@PathVariable Long boardId,
        @RequestBody NoticeBoardRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.updateNoticeBoard(boardId, requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지글 단건조회
    @GetMapping("/boards/notices/{boardId}")
    public ResponseEntity getNoticeBoard(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(boardId,
            userDetails.getUser());
        return new ResponseEntity(noticeBoard, HttpStatus.OK);
    }

    //공지사항 전체조회
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
