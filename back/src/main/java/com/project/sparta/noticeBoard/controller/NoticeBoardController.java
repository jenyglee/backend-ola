package com.project.sparta.noticeBoard.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.service.NoticeBoardService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    //공지사항 작성
    @PostMapping("/notices")
    public ResponseEntity createNoticeBoard(@RequestBody NoticeBoardRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        noticeBoardService.createNoticeBoard(requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지사항 삭제
    @DeleteMapping("/notices/{boardId}")
    public ResponseEntity deleteNoticeBoard(@PathVariable Long boardId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        noticeBoardService.deleteNoticeBoard(boardId, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지사항 수정
    @PutMapping("/notices/{boardId}")
    public ResponseEntity updateNoticeBoard(@PathVariable Long boardId,
                                            @RequestBody NoticeBoardRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        noticeBoardService.updateNoticeBoard(boardId, requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지글 단건조회
    @GetMapping("/notices/{boardId}")
    public ResponseEntity getNoticeBoard(@PathVariable Long boardId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(boardId, userDetails.getUser());
        return new ResponseEntity(noticeBoard, HttpStatus.OK);
    }

    //공지사항 전체조회
    @GetMapping("/notices")
    public ResponseEntity getNoticeBoardList(@RequestParam int page,
                                             @RequestParam int size,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(page, size, userDetails.getUser());
        return new ResponseEntity(allNoticeBoard, HttpStatus.OK);
    }

}
