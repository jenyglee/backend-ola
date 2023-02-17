package com.project.sparta.communityComment.controller;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
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
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.dto.CommunityResponseDto;

@RestController
@RequiredArgsConstructor
public class CommunityCommnetController {

  private final CommunityCommentService commentService;

  //댓글 생성
  @PostMapping("/comments/communities/{boards_Id}")
  public ResponseEntity createCommunityComment(@PathVariable Long boards_Id
      , @RequestBody CommunityRequestDto communityRequestDto
      , @AuthenticationPrincipal UserDetailsImpl userDetail) {
    CommunityResponseDto communityResponseDto = commentService.createCommunityComments(boards_Id,
        communityRequestDto, userDetail.getUser());
    return new ResponseEntity<>(communityResponseDto, HttpStatus.OK);
  }

  //선택한 댓글 수정
  @PatchMapping("/comments/{comment_id}/communities/{boards_Id}")
  public ResponseEntity updateCommunityComment(
      @PathVariable Long boards_Id,
      @PathVariable Long comment_id,
      @RequestBody CommunityRequestDto communityRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetail) {
    CommunityResponseDto communityResponseDto = commentService.updateCommunityComments(boards_Id,comment_id,
        communityRequestDto, userDetail.getUser());
    return new ResponseEntity<>(communityResponseDto, HttpStatus.OK);
  }

  //선택한 댓글 삭제
  @DeleteMapping("/comments/{comment_id}/communities/{boards_id}")
  public ResponseEntity deleteCommunityComment(@PathVariable Long boards_id,
      @PathVariable Long comment_id
      ,@AuthenticationPrincipal UserDetailsImpl userDetail) {
    commentService.deleteCommunityComments(boards_id, comment_id,userDetail.getUser());
    return new ResponseEntity("댓글 삭제 완료", HttpStatus.OK);
  }


}
