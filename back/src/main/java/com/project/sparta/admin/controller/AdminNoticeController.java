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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import java.util.List;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"어드민 공지사항"})
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
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.createNoticeBoard(requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지사항 삭제
    @ApiOperation(value = "공지사항 삭제",response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "공지사항 ID", required = true, dataType = "Long", paramType = "path", example = "1")
    })
    @DeleteMapping("/boards/notices/{boardId}")
    public ResponseEntity deleteNotice(@PathVariable Long boardId,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.deleteNoticeBoard(boardId, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //공지사항 수정
    @ApiOperation(value = "공지사항 수정",response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "공지사항 ID", required = true, dataType = "Long", paramType = "path", example = "1")
    })
    @PatchMapping("/boards/notices/{boardId}")
    public ResponseEntity updateNotice(@PathVariable Long boardId,
        @RequestBody NoticeBoardRequestDto requestDto,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        noticeBoardService.updateNoticeBoard(boardId, requestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }
    @ApiOperation(value = "공지글 단건조회", notes = "회원 전용 단건 조회")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "공지사항 ID", required = true, dataType = "Long", paramType = "path", example = "1")
    })
    @GetMapping("/boards/notices/{boardId}")
    public ResponseEntity getNoticeBoard(@PathVariable Long boardId) {
        NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(boardId);
        return new ResponseEntity(noticeBoard, HttpStatus.OK);
    }

    //공지사항 전체조회
    @ApiOperation(value = "공지글 전체조회", notes = "회원 전용 전체 조회")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "category", value = "카테고리명(SERVICE/EVENT/UPDATE)", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "보여줄 개수", required = true, dataType = "int", paramType = "query", defaultValue = "10", example = "10")
    })
    @GetMapping("/boards/notices")
    public ResponseEntity getNoticeBoardList(@RequestParam int page,
        @RequestParam int size,
        @RequestParam String category) {
        PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(
            page, size, category);
        return new ResponseEntity(allNoticeBoard, HttpStatus.OK);
    }
}
