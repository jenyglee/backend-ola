//package com.project.sparta.communityComment.controller;
//
//import com.project.sparta.communityComment.dto.CommunityRequestDto;
//import com.project.sparta.communityComment.service.CommunityCommentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class CommunityCommnetController {
//  private final CommunityCommentService commentService;
//  @PostMapping("/comments")
//  public ResponseEntity createComments(@PathVariable Long communityBoardId,@RequestBody CommunityRequestDto communityRequestDto) {
//    commentService.createComments(communityBoardId,communityRequestDto);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
//
//  @PatchMapping("/comments/{boardId}")
//  public ResponseEntity updateComments(@PathVariable Long boardId,
//      @RequestBody CommunityRequestDto communityRequestDto) {
//    return commentService.updateComments(boardId,communityRequestDto);
//  }
//
//  //선택한 게시글 삭제
//  @DeleteMapping("/comments/{boardId}")
//  public ResponseEntity deleteBoard(@PathVariable Long boardId) {
//    return commentService.deleteComments(boardId);
//  }
//}
