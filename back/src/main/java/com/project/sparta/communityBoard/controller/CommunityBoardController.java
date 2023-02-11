package com.project.sparta.communityBoard.controller;

import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
//import com.project.sparta.communityComment.controller.CommunityCommnetController;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.dto.CommunityResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.service.CommunityCommentService;
import com.project.sparta.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommunityBoardController {
  private final CommunityBoardService communityBoardService;
  @PostMapping("/board")
  public ResponseEntity createCommunityBoard(@RequestBody CommunityBoardRequestDto communityBoardRequestDto
      , @AuthenticationPrincipal UserDetailsImpl userDetail) {
    CommunityBoardResponseDto communityBoardResponseDto = communityBoardService.createCommunityBoard(
        communityBoardRequestDto, userDetail.getUser());
    return new ResponseEntity<>(communityBoardResponseDto, HttpStatus.OK);
  }

  @PatchMapping("/board")
  public ResponseEntity updateCommunityBoard(@RequestBody CommunityBoardRequestDto communityBoardRequestDto
      , @AuthenticationPrincipal UserDetailsImpl userDetail) {
    CommunityBoardResponseDto communityBoardResponseDto = communityBoardService.createCommunityBoard(
        communityBoardRequestDto, userDetail.getUser());
    return new ResponseEntity<>(communityBoardResponseDto, HttpStatus.OK);
  }

  //선택한 게시글 삭제
  @DeleteMapping("/board/{boardId}")
  public ResponseEntity deleteCommunityComment(@AuthenticationPrincipal UserDetailsImpl userDetail) {

    communityBoardService.deleteCommunityBoard(userDetail.getUser());
    return new ResponseEntity("보드 삭제 완료", HttpStatus.OK);
  }

  //게시글 전부 삭제 (운영자권한)
  @DeleteMapping("/board/delete")
  public ResponseEntity allDeleteCommunityComments() {

    communityBoardService.deleteAllCommunityBoard();
    return new ResponseEntity("보드 전부 삭제 완료", HttpStatus.OK);
  }

}
