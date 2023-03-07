package com.project.sparta.noticeBoard.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.service.NoticeBoardService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum.Authority;
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
@Api(tags = {"공지사항"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    //공지글 단건조회
    @ApiOperation(value = "공지글 단건조회", notes = "회원 전용 단건 조회")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "공지사항 ID", required = true, dataType = "Long", paramType = "path", example = "1")
    })
    @GetMapping("/notices/{boardId}")
    public ResponseEntity getNoticeBoard(@PathVariable Long boardId){
        NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(boardId);
        return new ResponseEntity(noticeBoard, HttpStatus.OK);
    }

    //공지사항 전체조회
    @ApiOperation(value = "공지글 전체조회", notes = "회원 전용 전체 조회")
    @GetMapping("/notices")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "category", value = "카테고리명(SERVICE/EVENT/UPDATE)", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "보여줄 개수", required = true, dataType = "int", paramType = "query", defaultValue = "10", example = "10")
    })
    public ResponseEntity getNoticeBoardList(@RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam String category){
        PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(page, size, category);
        return new ResponseEntity(allNoticeBoard, HttpStatus.OK);
    }

}
