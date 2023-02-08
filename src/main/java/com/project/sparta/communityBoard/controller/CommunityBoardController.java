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
import com.project.sparta.security.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  @PostMapping("/comments/{boardId}")
  public ResponseEntity createCommunityBoard(@RequestBody CommunityBoardRequestDto communityBoardRequestDto
      , @AuthenticationPrincipal String nickName) {
    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(
        communityBoardRequestDto, nickName);
    return new ResponseEntity<>(new CommunityBoardResponseDto(communityBoard), HttpStatus.OK);
  }

}
