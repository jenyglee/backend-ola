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
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    //공지글 작성
    @PostMapping("/notice-boards")
    public ResponseEntity createNoticeBoard(@RequestBody NoticeBoardRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        noticeBoardService.createNoticeBoard(requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지글 삭제
    @DeleteMapping("/notice-boards/{id}")
    public ResponseEntity deleteNoticeBoard(@PathVariable Long id,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        noticeBoardService.deleteNoticeBoard(id, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지글 수정
    @PutMapping("/notice-boards/{id}")
    public ResponseEntity updateNoticeBoard(@PathVariable Long id,
                                            @RequestBody NoticeBoardRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        noticeBoardService.updateNoticeBoard(id, requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지글 단건조회
    @GetMapping("/notice-boards/{id}")
    public ResponseEntity getNoticeBoard(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(id, userDetails.getUser());
        return new ResponseEntity(noticeBoard, HttpStatus.OK);
    }

    //공지글 전체조회
    @GetMapping("/notice-boards")
    public ResponseEntity getNoticeBoardList(@RequestParam int offset,
                                             @RequestParam int limit,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(offset, limit, userDetails.getUser());
        return new ResponseEntity(allNoticeBoard, HttpStatus.OK);
    }

}
