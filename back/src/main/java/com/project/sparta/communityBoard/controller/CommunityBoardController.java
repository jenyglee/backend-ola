package com.project.sparta.communityBoard.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.AllCommunityBoardResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
//import com.project.sparta.communityComment.controller.CommunityCommnetController;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.dto.CommunityResponseDto;
import com.project.sparta.communityComment.dto.CommunityWithLikeResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.service.CommunityCommentService;
import com.project.sparta.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommunityBoardController {
  private final CommunityBoardService communityBoardService;

  //커뮤니티 작성
  @PostMapping("/communities")
  public ResponseEntity createCommunityBoard(@RequestBody CommunityBoardRequestDto communityBoardRequestDto
      , @AuthenticationPrincipal UserDetailsImpl userDetail) {
    communityBoardService.createCommunityBoard(communityBoardRequestDto, userDetail.getUser());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  //커뮤니티 단건 조회
  @GetMapping("/communities/{boardId}")
  public ResponseEntity getCommunityBoard(@PathVariable Long boardId) {
    CommunityBoardResponseDto communityBoardResponseDto = communityBoardService.getCommunityBoard(boardId);
    return new ResponseEntity<>(communityBoardResponseDto,HttpStatus.OK);
  }

  // TODO 커뮤니티 전체 조회 -> 필터링 기능 구현
  //커뮤니티 전체 조회
  @GetMapping("/communities")
  public ResponseEntity getAllCommunityBoard(
      @RequestParam("page") int page,
      @RequestParam("size") int size) {
    PageResponseDto<List<AllCommunityBoardResponseDto>> communityBoardResponseDto = communityBoardService.getAllCommunityBoard(page,size);
    return new ResponseEntity<>(communityBoardResponseDto,HttpStatus.OK);
  }

  //커뮤니티 수정
  @PatchMapping("/communities/{boardId}")
  public ResponseEntity updateCommunityBoard(@PathVariable Long boardId, @RequestBody CommunityBoardRequestDto communityBoardRequestDto
      ,@AuthenticationPrincipal UserDetailsImpl userDetail) {
    communityBoardService.updateCommunityBoard(boardId, communityBoardRequestDto, userDetail.getUser());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  //커뮤니티 삭제
  @DeleteMapping("/communities/{boardId}")
  public ResponseEntity deleteCommunityBoard(@PathVariable Long boardId,@AuthenticationPrincipal UserDetailsImpl userDetail) {
    communityBoardService.deleteCommunityBoard(boardId,userDetail.getUser());
    return new ResponseEntity("보드 삭제 완료", HttpStatus.OK);
  }
}
