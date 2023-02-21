package com.project.sparta.noticeBoard.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.service.NoticeBoardService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum.Authority;
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
@Api(tags = {"공지사항 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
@PreAuthorize(Authority.ADMIN)
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    //공지글 단건조회
    @ApiOperation(value = "공지글 단건조회",response = Join.class)
    @GetMapping("/notices/{boardId}")
    public ResponseEntity getNoticeBoard(@PathVariable Long boardId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(boardId, userDetails.getUser());
        return new ResponseEntity(noticeBoard, HttpStatus.OK);
    }

    //공지사항 전체조회
    @ApiOperation(value = "공지글 전체조회",response = Join.class)
    @GetMapping("/notices")
    public ResponseEntity getNoticeBoardList(@RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam String category,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(page, size, category, userDetails.getUser());
        return new ResponseEntity(allNoticeBoard, HttpStatus.OK);
    }

}
