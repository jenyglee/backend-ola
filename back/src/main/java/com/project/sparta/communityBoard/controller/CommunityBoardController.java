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
public class CommunityBoardController {
  private final CommunityBoardService communityBoardService;

  //게시글 생성
  @PostMapping("/boards/communities")
  public ResponseEntity createCommunityBoard(@RequestBody CommunityBoardRequestDto communityBoardRequestDto
      , @AuthenticationPrincipal UserDetailsImpl userDetail) {
    communityBoardService.createCommunityBoard(communityBoardRequestDto, userDetail.getUser());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  //게시글 단건조회
  @GetMapping("/boards/communities/{boards_id}")
  public ResponseEntity getCommunityBoard(@PathVariable Long boards_id) {
    CommunityBoardResponseDto communityBoardResponseDto = communityBoardService.getCommunityBoard(boards_id);
    return new ResponseEntity<>(communityBoardResponseDto,HttpStatus.OK);
  }

  //게시글 전체조회

  @GetMapping("/community_boards")
  public ResponseEntity getAllCommunityBoard(
      @RequestParam("page") int page,
      @RequestParam("size") int size) {

    PageResponseDto<List<AllCommunityBoardResponseDto>> communityBoardResponseDto = communityBoardService.getAllCommunityBoard(page,size);
    return new ResponseEntity<>(communityBoardResponseDto,HttpStatus.OK);

  }




  //게시글 수정
  @PatchMapping("/boards/communities/{boards_id}")
  public ResponseEntity updateCommunityBoard(@PathVariable Long boards_id, @RequestBody CommunityBoardRequestDto communityBoardRequestDto
      ,@AuthenticationPrincipal UserDetailsImpl userDetail) {
    communityBoardService.updateCommunityBoard(boards_id, communityBoardRequestDto, userDetail.getUser());
    return new ResponseEntity<>(HttpStatus.OK);
  }


  //게시글 삭제
  @DeleteMapping("/boards/communities/{boards_id}")
  public ResponseEntity deleteCommunityBoard(@PathVariable Long boards_id,@AuthenticationPrincipal UserDetailsImpl userDetail) {
    communityBoardService.deleteCommunityBoard(boards_id,userDetail.getUser());
    return new ResponseEntity("보드 삭제 완료", HttpStatus.OK);
  }


  //내가 쓴 게시물 조회

  @GetMapping("/boards/communities/me_boards")
  public ResponseEntity getMyBoardAll(
          @RequestParam("page") int page,
          @RequestParam("size") int size,
          @AuthenticationPrincipal UserDetailsImpl userDetails) {
    PageResponseDto<List<GetMyBoardResponseDto>> communityBoardResponseDto = communityBoardService.getMyCommunityBoard(page, size, userDetails.getUser());
    return new ResponseEntity<>(communityBoardResponseDto, HttpStatus.OK);

  }

}
