package com.project.sparta.communityComment.controller;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.service.CommunityCommentService;
import com.project.sparta.security.UserDetailImpl;
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
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.dto.CommunityResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommunityCommnetController {

  private final CommunityCommentService commentService;

  //댓글 생성
  @PostMapping("/comments/{communityBoardId}")
  public ResponseEntity createCommunityComment(@PathVariable Long communityBoardId
      , @RequestBody CommunityRequestDto communityRequestDto
      , @AuthenticationPrincipal UserDetailImpl userDetail) {
    CommunityComment communityComment = commentService.createCommunityComments(communityBoardId,
        communityRequestDto, userDetail.getUsername());
    return new ResponseEntity<>(new CommunityResponseDto(communityComment), HttpStatus.OK);
  }

  //선택한 댓글 수정
  @PatchMapping("/comments/{communityBoardId}")
  public ResponseEntity updateCommunityComment(@PathVariable Long communityBoardId
      , @RequestBody CommunityRequestDto communityRequestDto
      , @AuthenticationPrincipal UserDetailImpl userDetail) {
    CommunityComment communityComment = commentService.updateCommunityComments(communityBoardId, communityRequestDto,
        userDetail.getUsername());
    return new ResponseEntity<>(new CommunityResponseDto(communityComment), HttpStatus.OK);
  }

  //선택한 댓글 삭제
  @DeleteMapping("/comments/{boardId}")
  public ResponseEntity deleteCommunityComment(@PathVariable Long boardId) {

    commentService.deleteCommunityComments(boardId);
    return new ResponseEntity("댓글 삭제 완료", HttpStatus.OK);
  }

  //댓글 전부 삭제 (운영자권한)
  @DeleteMapping("/comments/delete")
  public ResponseEntity allDeleteCommunityComments() {

    commentService.allDeleteCommunityComments();
    return new ResponseEntity("댓글 전부 삭제 완료", HttpStatus.OK);
  }

}
